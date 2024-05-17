package com.dhbw.get2gether.backend.widget.model.shoppinglist;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EntryCheckCommand {
    private boolean checked;
}
