package com.dhbw.get2gether.backend.widget.model.expensesplit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class ExpenseEntryDto {
    private String id;
    private String creatorId;
    private String description;
    private double price;
    private List<UserWithPercentageDto> involvedUsers;
}
