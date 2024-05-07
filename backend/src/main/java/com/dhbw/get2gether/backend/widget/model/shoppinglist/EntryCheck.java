package com.dhbw.get2gether.backend.widget.model.shoppinglist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public class EntryCheck {
    private boolean isChecked;
}
