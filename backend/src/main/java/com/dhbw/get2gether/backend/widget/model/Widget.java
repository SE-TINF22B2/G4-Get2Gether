package com.dhbw.get2gether.backend.widget.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Widget {
    protected final String id;
    protected final LocalDateTime creationDate;
    protected final WidgetType widgetType;
}
