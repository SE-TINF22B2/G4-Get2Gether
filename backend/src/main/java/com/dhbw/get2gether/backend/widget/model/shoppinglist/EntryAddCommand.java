package com.dhbw.get2gether.backend.widget.model.shoppinglist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class EntryAddCommand {
    private String description;
    private String amount;
}
