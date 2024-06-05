package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.event.model.EventParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DebtDto {
    private EventParticipantDto user;
    private double debtAmount;
}
