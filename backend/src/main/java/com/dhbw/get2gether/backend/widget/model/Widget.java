package com.dhbw.get2gether.backend.widget.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class Widget {

    private final LocalDateTime creationDate;
    private final WidgetType widgetType;

    protected Widget(WidgetType widgetType) {
        this.widgetType = widgetType;
        this.creationDate = LocalDateTime.now();
    }
}
