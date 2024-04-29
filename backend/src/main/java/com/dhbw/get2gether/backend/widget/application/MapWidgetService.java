package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.widget.application.mapper.MapWidgetMapper;
import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import com.dhbw.get2gether.backend.widget.model.map.MapWidget;
import com.dhbw.get2gether.backend.widget.model.map.MapWidgetCreateCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class MapWidgetService extends AbstractWidgetService {

    private final MapWidgetMapper mapper;

    MapWidgetService(EventService eventService, MapWidgetMapper mapper) {
        super(eventService);
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createMapWidget(AuthenticatedPrincipal principal, String eventId, MapWidgetCreateCommand createCommand) {
        Event event = getEventById(principal, eventId);
        MapWidget widget = mapper.mapToMapWidget(createCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .build();
        return addWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public MapWidget addLocation(AuthenticatedPrincipal principal, String eventId, String widgetId, LocationAddCommand addCommand) {
        Event event = getEventById(principal, eventId);
        MapWidget widget = getWidgetFromEvent(event, widgetId);

        Location location = mapper.mapToLocation(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .build();

        widget.getLocations().add(location);
        return updateAndGetWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public MapWidget removeLocation(AuthenticatedPrincipal principal, String eventId, String widgetId, String locationId) {
        Event event = getEventById(principal, eventId);
        MapWidget widget = getWidgetFromEvent(event, widgetId);
        Location location = widget.getLocations().stream()
                .filter(l -> Objects.equals(l.getId(), locationId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        if (!widget.getLocations().remove(location)) {
            throw new IllegalStateException("Failed to remove location from event.");
        }
        return updateAndGetWidget(principal, event, widget);
    }

}
