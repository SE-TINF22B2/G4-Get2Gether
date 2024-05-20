package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeptDto {
    private SimpleUserDto user;
    private double deptAmount;
}
