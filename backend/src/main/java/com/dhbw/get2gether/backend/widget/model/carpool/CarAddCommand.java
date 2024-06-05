package com.dhbw.get2gether.backend.widget.model.carpool;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class CarAddCommand {
    private String driverAdress;
    @Min(1)
    private int anzahlPlaetze;
}
