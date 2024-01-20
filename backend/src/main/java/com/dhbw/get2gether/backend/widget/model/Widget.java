package com.dhbw.get2gether.backend.widget.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Widget {
    private LocalDateTime creationDate;

    private final WidgetType widgetType;

    protected Widget(WidgetType widgetType) {
        this.widgetType = widgetType;
    }
}
