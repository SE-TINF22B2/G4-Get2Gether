package com.dhbw.get2gether.backend.event.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EventCreateCommand {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private LocalDateTime endDate;
}
