package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.widget.model.IWidget;
import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.COMPILE_ERROR)
public abstract class WidgetMapper {

    // Manual implementation of @SubclassMapping which returns the widget object if no custom mapping is needed.
    // @SubclassMapping(source = ExpenseSplitWidget.class, target = ExpenseSplitWidgetDto.class)
    public IWidget widgetToIWidget(Widget widget, @Context List<SimpleUserDto> participants) {
        if (widget instanceof ExpenseSplitWidget) {
            return expenseSplitWidgetToExpenseSplitWidgetDto((ExpenseSplitWidget) widget, participants);
        }
        return widget;
    }

    abstract ExpenseSplitWidgetDto expenseSplitWidgetToExpenseSplitWidgetDto(ExpenseSplitWidget widget, @Context List<SimpleUserDto> participants);

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

    abstract UserWithPercentageDto userWithPercentageToUserWithPercentageDto(UserWithPercentage userWithPercentage, SimpleUserDto user);

}
