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

    public void check(String buyerId, EntryCheck entryCheck) {
        if(entryCheck.isChecked()) {
            this.checked = true;
            this.buyerId = buyerId;
        } else {
            this.checked = false;
            // BuyerID soll nicht gelöscht werden, wenn der Eintrag nicht mehr gecheckt ist, aber überschrieben werden können
        }
    }

    public void update(EntryUpdateCommand updateEntry) {
        this.description = updateEntry.getDescription();
        this.amount = updateEntry.getAmount();
    }
}
