package com.dhbw.get2gether.backend.widget.model.shoppinglist;

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
public class ShoppingListWidget extends Widget {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<Entry> entries = new ArrayList<>();

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.SHOPPING_LIST;
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public boolean removeEntry(Entry entry) {
        return entries.remove(entry);
    }
}

