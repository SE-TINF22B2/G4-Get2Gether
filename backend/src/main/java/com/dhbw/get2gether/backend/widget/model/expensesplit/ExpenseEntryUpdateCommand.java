package com.dhbw.get2gether.backend.widget.model.expensesplit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ExpenseEntryUpdateCommand {
    private String description;
    private double price;
    private List<String> involvedUsers;
}
