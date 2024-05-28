package com.dhbw.get2gether.backend.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class EventUpdateCommand {
    private String name;
    private String description;
    private EventLocation location;
    private LocalDateTime date;
    private LocalDateTime endDate;
}
