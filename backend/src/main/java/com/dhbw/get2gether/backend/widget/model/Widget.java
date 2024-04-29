package com.dhbw.get2gether.backend.widget.model;

import java.time.LocalDateTime;

public abstract class Widget {
    //todo Lohnt es sich hier nicht schon id und creationDate zu deklarieren?
    public abstract String getId();
    public abstract LocalDateTime getCreationDate();
    public abstract WidgetType getWidgetType();
}
