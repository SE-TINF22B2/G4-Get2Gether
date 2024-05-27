package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.widget.model.IWidget;
import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.COMPILE_ERROR)
public abstract class WidgetMapper {

    // Manual implementation of @SubclassMapping which returns the widget object if no custom mapping is needed.
    // @SubclassMapping(source = ExpenseSplitWidget.class, target = ExpenseSplitWidgetDto.class)
    public IWidget widgetToIWidget(Widget widget, @Context List<SimpleUserDto> participants, @Context Optional<String> userId) {
        if (widget instanceof ExpenseSplitWidget) {
            List<Debt> debts = userId.map(id -> ((ExpenseSplitWidget) widget).calculateDebtsForUserId(id))
                    .orElse(new ArrayList<>());
            return expenseSplitWidgetToExpenseSplitWidgetDto((ExpenseSplitWidget) widget, debts, participants);
        }
        return widget;
    }

    public abstract ExpenseSplitWidgetDto expenseSplitWidgetToExpenseSplitWidgetDto(ExpenseSplitWidget widget, List<Debt> debts, @Context List<SimpleUserDto> participants);

    // Find the userWithPercentage in the list of participants. Return null if the user is not found.
    UserWithPercentageDto userWithPercentageToUserWithPercentageDto(UserWithPercentage userWithPercentage, @Context List<SimpleUserDto> participants) {
        return participants.stream().filter(p -> p.getId().equals(userWithPercentage.getUserId()))
                .findFirst()
                .map(user -> userWithPercentageToUserWithPercentageDto(userWithPercentage, user))
                .orElse(null);
    }

    ExpenseEntryDto expenseEntryToExpenseEntryDto(ExpenseEntry expenseEntry, @Context List<SimpleUserDto> participants){
        if(expenseEntry.getInvolvedUsers().isEmpty()) {
            throw new IllegalArgumentException("At least one user must be involved in the expense entry");
        }
        return ExpenseEntryDto.builder()
                .id(expenseEntry.getId())
                .description(expenseEntry.getDescription())
                .creatorId(expenseEntry.getCreatorId())
                .price(expenseEntry.getPrice())
                .involvedUsers(expenseEntry.getInvolvedUsers().stream().map(user -> userWithPercentageToUserWithPercentageDto(user, participants)).toList())
                .percentagePerPerson(1.0 /expenseEntry.getInvolvedUsers().size())
                .pricePerPerson(expenseEntry.getPrice()/expenseEntry.getInvolvedUsers().size())
                .build();
    }

    DebtDto debtToDebtDto(Debt debt, @Context List<SimpleUserDto> participants){
        return DebtDto.builder()
                .debtAmount(debt.getDebtAmount())
                .user(participants.stream().filter(user -> user.getId().equals(debt.getUserId())).findFirst().orElseThrow(
                        () -> new EntityNotFoundException("User not in participants")
                )).build();
    }

    abstract UserWithPercentageDto userWithPercentageToUserWithPercentageDto(UserWithPercentage userWithPercentage, SimpleUserDto user);

}
