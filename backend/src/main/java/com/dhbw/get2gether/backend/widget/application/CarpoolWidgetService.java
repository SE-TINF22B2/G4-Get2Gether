package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.widget.application.mapper.CarpoolWidgetMapper;
import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolCreateCommand;
import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolWidget;
import com.dhbw.get2gether.backend.widget.model.carpool.Rider;
import com.dhbw.get2gether.backend.widget.model.carpool.RiderAddCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class CarpoolWidgetService extends AbstractWidgetService{

    private final CarpoolWidgetMapper mapper;
    private final UserService userService;

    CarpoolWidgetService(EventService eventService, CarpoolWidgetMapper mapper, UserService userService) {
        super(eventService);
        this.mapper = mapper;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createCarpoolWidget(AuthenticatedPrincipal principal, String eventId, CarpoolCreateCommand createCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = mapper.mapToCarpoolWidget(createCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .driverAdress(mapper.mapToLocation(createCommand.getDriverAdress()))
                .build();
        return addWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidget addRider(AuthenticatedPrincipal principal, String eventId, String widgetId, RiderAddCommand addCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Rider rider = mapper.mapToRider(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .userId(userService.getUserByPrincipal(principal).getId())
                .pickupPlace(mapper.mapToLocation(addCommand.getPickupPlace()))
                .build();

        widget.addRider(rider);
        return updateAndGetWidget(principal, event, widget);
    }
    @PreAuthorize("hasRole('USER')")
    public CarpoolWidget removeRider(AuthenticatedPrincipal principal, String eventId, String widgetId, String riderId) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Rider rider = widget.getRiders().stream()
                .filter(r -> Objects.equals(r.getId(), riderId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Rider not found"));
        if (!widget.removeRider(rider)) {
            throw new IllegalStateException("Failed to remove rider from shopping list widget");
        }
        return updateAndGetWidget(principal, event, widget);
    }

}
