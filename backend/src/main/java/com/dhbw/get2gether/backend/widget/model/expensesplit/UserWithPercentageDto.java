package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.event.model.EventParticipantDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserWithPercentageDto {
    private EventParticipantDto user;
    private double percentage;
}
