package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Car {
    private String id;
    private String driverId;
    private String driverAdress;
    private int anzahlPlaetze;
    @Builder.Default
    private List<Rider> riders = new ArrayList<>();

    public void addRider(Rider rider) {
        if (riders.stream().anyMatch(r -> Objects.equals(r.getUserId(), rider.getUserId())))
            throw new OperationNotAllowedException("Rider already exists");
        if (riders.size() >= anzahlPlaetze)
            throw new OperationNotAllowedException("Car is already full");
        if (Objects.equals(driverId, rider.getUserId()))
            throw new OperationNotAllowedException("Driver cannot be added as rider");
        riders.add(rider);
    }

    public boolean removeRider(Rider rider) {
        if (!riders.contains(rider))
            throw new OperationNotAllowedException("Rider does not exist");
        return riders.remove(rider);
    }
}
