package com.dhbw.get2gether.backend.widget.model;

import java.time.LocalDateTime;

public interface IWidget {
    String getId();
    LocalDateTime getCreationDate();
    WidgetType getWidgetType();
}
