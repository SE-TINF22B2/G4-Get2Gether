package com.dhbw.get2gether.backend.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventLocation {
    private String street;
    private String postalCode;
    private String city;
}
