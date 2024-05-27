package com.dhbw.get2gether.backend.widget.model.carpool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class CarUpdateCommand {
    private String driverAdress;
    private int anzahlPlaetze;
}
