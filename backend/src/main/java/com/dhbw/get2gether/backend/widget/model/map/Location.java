package com.dhbw.get2gether.backend.widget.model.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {

    private final String id; // place id from Google Maps
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;

}
