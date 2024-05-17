package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarpoolCreateCommand {
    private LocationAddCommand driverAdress;
    private int anzahlPlaetze;
    private String[] riderIds;
}
