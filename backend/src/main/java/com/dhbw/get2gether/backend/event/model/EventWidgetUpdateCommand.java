package com.dhbw.get2gether.backend.event.model;

import com.dhbw.get2gether.backend.widget.model.Widget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class EventWidgetUpdateCommand {
    private List<Widget> widgets;
}
