package com.dhbw.get2gether.backend.widget.application.mapper;

import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseSplitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entries", ignore = true)
    ExpenseSplitWidget mapToExpenseSplitWidget(ExpenseSplitWidgetCreateCommand createCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "involvedUsers", ignore = true)
    ExpenseEntry mapToEntry(ExpenseEntryAddCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "involvedUsers", ignore = true)
    ExpenseEntry mapToEntry(ExpenseEntryUpdateCommand command);
}
