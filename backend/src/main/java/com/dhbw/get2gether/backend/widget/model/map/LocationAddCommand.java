package com.dhbw.get2gether.backend.widget.model.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LocationAddCommand {
    private final String placeId;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;
}
