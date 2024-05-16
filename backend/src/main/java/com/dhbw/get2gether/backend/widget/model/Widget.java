package com.dhbw.get2gether.backend.widget.model;

import java.time.LocalDateTime;

public abstract class Widget implements WidgetInterface {
    public abstract String getId();
    public abstract LocalDateTime getCreationDate();
    public abstract WidgetType getWidgetType();
}
