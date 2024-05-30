package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.widget.application.mapper.CarpoolWidgetMapper;
import com.dhbw.get2gether.backend.widget.application.mapper.WidgetMapper;
import com.dhbw.get2gether.backend.widget.model.carpool.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarpoolWidgetService extends AbstractWidgetService{

    private final CarpoolWidgetMapper mapper;
    private final UserService userService;
    private final WidgetMapper widgetMapper;

    CarpoolWidgetService(EventService eventService, CarpoolWidgetMapper mapper, UserService userService, WidgetMapper widgetMapper) {
        super(eventService);
        this.mapper = mapper;
        this.userService = userService;
        this.widgetMapper = widgetMapper;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createCarpoolWidget(AuthenticatedPrincipal principal, String eventId, CarpoolCreateCommand createCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = mapper.mapToCarpoolWidget(createCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .build();
        return addWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidgetDto addCar(AuthenticatedPrincipal principal, String eventId, String widgetId, CarAddCommand addCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);

        Car car = mapper.mapToCar(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .driverId(userService.getUserByPrincipal(principal).getId())
                .build();
        widget.addCar(car);
        return mapToDto(updateAndGetWidget(principal, event, widget));
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidgetDto updateCar(OAuth2User principal, String eventId, String widgetId, String carId, CarUpdateCommand updateCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Car originalCar = widget.getCars().stream()
                .filter(l -> Objects.equals(l.getId(), carId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        Car updatedCar = mapper.mapToCar(updateCommand).toBuilder()
                .id(originalCar.getId())
                .driverId(originalCar.getDriverId())
                .riders(originalCar.getRiders())
                .build();
        if(!widget.replaceCar(originalCar, updatedCar)) {
            throw new IllegalStateException("Failed to replace car from carpool widget");
        }
        return mapToDto(updateAndGetWidget(principal, event, widget));
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidgetDto removeCar(AuthenticatedPrincipal principal, String eventId, String widgetId, String carId) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Car car = widget.getCars().stream()
                .filter(c -> Objects.equals(c.getId(), carId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        if (!widget.removeCar(car)) {
            throw new IllegalStateException("Failed to remove Car from Carpool widget");
        }
        return mapToDto(updateAndGetWidget(principal, event, widget));
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidgetDto addRider(AuthenticatedPrincipal principal, String eventId, String widgetId, String carId, RiderAddCommand addCommand) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Car car = widget.getCars().stream()
                .filter(c -> Objects.equals(c.getId(), carId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        Rider rider = mapper.mapToRider(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .userId(userService.getUserByPrincipal(principal).getId())
                .build();
        car.addRider(rider);
        return mapToDto(updateAndGetWidget(principal, event, widget));
    }

    @PreAuthorize("hasRole('USER')")
    public CarpoolWidgetDto removeRider(AuthenticatedPrincipal principal, String eventId, String widgetId, String carId, String riderId) {
        Event event = getEventById(principal, eventId);
        CarpoolWidget widget = getWidgetFromEvent(event, widgetId);
        Car car = widget.getCars().stream()
                .filter(c -> Objects.equals(c.getId(), carId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        Rider rider = car.getRiders().stream()
                .filter(r -> Objects.equals(r.getId(), riderId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Rider not found"));
        if (!car.removeRider(rider)) {
            throw new IllegalStateException("Failed to remove rider from car");
        }
        return mapToDto(updateAndGetWidget(principal, event, widget));
    }

    private CarpoolWidgetDto mapToDto(CarpoolWidget widget) {
        List<SimpleUserDto> simpleUserDtos = userService.getSimpleUsersById(
                widget.getCars().stream()
                        .flatMap(car -> car.getRiders().stream())
                        .map(Rider::getUserId)
                        .collect(Collectors.toList()));
        return widgetMapper.carpoolWidgetToCarpoolWidgetDto(widget, simpleUserDtos);
    }
}
