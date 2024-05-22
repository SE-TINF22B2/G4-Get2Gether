package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class RiderAddCommand {
    private final String pickupPlace;
}
