package com.dhbw.get2gether.backend.widget.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class Widget {

    private final String id;
    private final LocalDateTime creationDate;
    private final WidgetType widgetType;

    protected Widget(WidgetType widgetType) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.widgetType = widgetType;
    }
}
