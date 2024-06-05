package com.dhbw.get2gether.backend.widget.model.carpool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RiderAddCommand {
    private String pickupPlace;
}
