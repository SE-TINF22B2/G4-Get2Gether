package com.dhbw.get2gether.backend.widget.model.shoppinglist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Entry {
    private String id;
    private boolean checked;
    private String creatorId;
    private String description;
    private String amount;
    private String buyerId;

    public void check(String buyerId) {
        this.checked = true;
        this.buyerId = buyerId;
    }
}
