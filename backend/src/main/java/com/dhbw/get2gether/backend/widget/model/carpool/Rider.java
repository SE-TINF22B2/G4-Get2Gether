package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.map.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public class Rider {
    private String userId;
    private String pickupPlace;
}
