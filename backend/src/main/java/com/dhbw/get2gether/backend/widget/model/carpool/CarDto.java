package com.dhbw.get2gether.backend.widget.model.carpool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class CarDto {
    private String id;
    private String driverId;
    private String driverAdress;
    private int anzahlPlaetze;
    private List<RiderDto> riders;

}
