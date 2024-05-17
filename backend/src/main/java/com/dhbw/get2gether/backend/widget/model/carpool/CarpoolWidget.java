package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.Entry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class CarpoolWidget extends Widget {
    private String id;
    private final LocalDateTime creationDate;
    private String driverId;
    private Location driverAdress;
    private int anzahlPlaetze;
    @Builder.Default
    private List<Rider> riders = new ArrayList<>();

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.CARPOOL;
    }
    public void addRider(Rider rider) {
        riders.add(rider);
    }

    public boolean removeRider(Rider rider) {
        return riders.remove(rider);
    }
}
