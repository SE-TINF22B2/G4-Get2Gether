package com.dhbw.get2gether.backend.widget.model.expensesplit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class ExpenseEntryAddCommand {
    private String description;
    private double price;
    private List<String> involvedUsers;
}
