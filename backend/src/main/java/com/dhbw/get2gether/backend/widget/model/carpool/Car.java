package com.dhbw.get2gether.backend.widget.model.carpool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Car {
    private String id;
    private String driverId;
    private String driverAdress;
    private int anzahlPlaetze;
    private List<Rider> riders;

  /*  public void addRider(Rider rider) {
        riders.add(rider.);
    }

    public boolean removeRider(Rider rider) {
        return riders.remove(rider);
    }
*/}
