package com.dhbw.get2gether.backend.widget.model.expensesplit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserWithPercentage {
    private String userId;
    private double percentage;
}
