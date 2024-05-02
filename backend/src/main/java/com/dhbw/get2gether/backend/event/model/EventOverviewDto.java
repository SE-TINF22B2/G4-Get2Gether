package com.dhbw.get2gether.backend.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventOverviewDto {
    private String id;
    private String name;
    private LocalDateTime date;
    private LocalDateTime endDate;
}
