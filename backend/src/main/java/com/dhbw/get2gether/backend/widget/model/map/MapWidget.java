package com.dhbw.get2gether.backend.widget.model.map;

import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MapWidget extends Widget {

    private final List<Location> locations;

    @Builder(toBuilder = true)
    public MapWidget(String id, LocalDateTime creationDate) {
        super(id, creationDate, WidgetType.MAP);
        this.locations = new ArrayList<>();
    }

}
