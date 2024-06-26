package com.dhbw.get2gether.backend.widget.model.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Location {
    private final String id;
    private final String placeId;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;
}
