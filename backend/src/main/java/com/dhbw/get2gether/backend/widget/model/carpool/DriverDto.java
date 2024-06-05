package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.event.model.EventParticipantDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class DriverDto {
    private EventParticipantDto user;
}
