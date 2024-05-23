package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
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
    @Builder.Default
    private List<Car> cars = new ArrayList<>();

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.CARPOOL;
    }
    public void addCar(Car car) {
        cars.add(car);
    }

    public boolean removeCar(Car car) {
        return cars.remove(car);
    }
}
