package com.dhbw.get2gether.backend.event.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class EventUpdateCommand {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
}
