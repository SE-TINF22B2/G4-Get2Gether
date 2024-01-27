package com.dhbw.get2gether.backend.event.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventCreateCommand {
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
}
