package com.dhbw.get2gether.backend.widget.model.expensesplit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class ExpenseSplitWidgetDto {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<ExpenseEntryDto> entries = new ArrayList<>();
}

