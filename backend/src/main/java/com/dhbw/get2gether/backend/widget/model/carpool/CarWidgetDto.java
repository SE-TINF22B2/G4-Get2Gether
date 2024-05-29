package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.widget.model.IWidget;
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
public class CarWidgetDto implements IWidget {
    private String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<CarDto> cars = new ArrayList<>();

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.CARPOOL;
    }
}
