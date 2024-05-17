package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.widget.model.Widget;
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
public class ExpenseSplitWidget extends Widget {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<ExpenseEntry> entries = new ArrayList<>();

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.EXPENSE_SPLIT;
    }

    public void addEntry(ExpenseEntry entry) {
        entries.add(entry);
    }

    public boolean removeEntry(ExpenseEntry entry) {
        return entries.remove(entry);
    }

    public boolean replaceEntry(ExpenseEntry oldEntry, ExpenseEntry newEntry) {
        int index = entries.indexOf(oldEntry);
        if (index == -1) {
            return false;
        }
        entries.set(index, newEntry);
        return true;
    }
}

