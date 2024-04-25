package com.dhbw.get2gether.backend.widget.model.map;

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
public class MapWidget extends Widget {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<Location> locations = new ArrayList<>();

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.MAP;
    }
}
