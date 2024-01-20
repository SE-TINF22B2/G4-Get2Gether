package com.dhbw.get2gether.backend.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class EventUpdateCommand {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
}
