package com.dhbw.get2gether.backend.event.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventUpdateCommand {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
}
