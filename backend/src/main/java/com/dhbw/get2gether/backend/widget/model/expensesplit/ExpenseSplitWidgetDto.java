package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.widget.model.IWidget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class ExpenseSplitWidgetDto implements IWidget {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<ExpenseEntryDto> entries = new ArrayList<>();
    @Builder.Default
    private List<DebtDto> debts = new ArrayList<>();


    @Override
    public WidgetType getWidgetType() {
        return WidgetType.EXPENSE_SPLIT;
    }
}

