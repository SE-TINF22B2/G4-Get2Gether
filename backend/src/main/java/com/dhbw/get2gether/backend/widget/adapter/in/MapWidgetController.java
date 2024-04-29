package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.widget.application.MapWidgetService;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import com.dhbw.get2gether.backend.widget.model.map.MapWidget;
import com.dhbw.get2gether.backend.widget.model.map.MapWidgetCreateCommand;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/{eventId}/widgets/map")
public class MapWidgetController {

    private final MapWidgetService service;

    MapWidgetController(MapWidgetService mapWidgetService) {
        this.service = mapWidgetService;
    }

    @PostMapping("/")
    public Event createMapWidget(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody MapWidgetCreateCommand createCommand
    ) {
        return service.createMapWidget(principal, eventId, createCommand);
    }

    @PostMapping("/{widgetId}/locations")
    public MapWidget addLocation(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody LocationAddCommand addCommand
    ) {
        return service.addLocation(principal, eventId, widgetId, addCommand);
    }

    @DeleteMapping("/{widgetId}/locations/{locationId}")
    public MapWidget removeLocation(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String locationId
    ) {
        return service.removeLocation(principal, eventId, widgetId, locationId);
    }

}
