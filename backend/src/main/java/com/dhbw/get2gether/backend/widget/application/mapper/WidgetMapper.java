package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.widget.model.IWidget;
import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.expensesplit.ExpenseSplitWidget;
import com.dhbw.get2gether.backend.widget.model.expensesplit.ExpenseSplitWidgetDto;
import com.dhbw.get2gether.backend.widget.model.expensesplit.UserWithPercentage;
import com.dhbw.get2gether.backend.widget.model.expensesplit.UserWithPercentageDto;
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

    abstract UserWithPercentageDto userWithPercentageToUserWithPercentageDto(UserWithPercentage userWithPercentage, SimpleUserDto user);

}
