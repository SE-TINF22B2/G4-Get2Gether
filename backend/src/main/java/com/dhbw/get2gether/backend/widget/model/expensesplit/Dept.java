package com.dhbw.get2gether.backend.widget.model.expensesplit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    private String userId;
    private double deptAmount;
}
