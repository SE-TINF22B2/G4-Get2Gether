package com.dhbw.get2gether.backend.widget.model.map;

import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MapWidget extends Widget {

    private final List<Location> locations;

    public MapWidget() {
        super(WidgetType.MAP);
        this.locations = new ArrayList<>();
    }

}
