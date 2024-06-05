package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventDetailDto;
import com.dhbw.get2gether.backend.widget.application.CarpoolWidgetService;
import com.dhbw.get2gether.backend.widget.model.carpool.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/{eventId}/widgets/carpool")
public class CarpoolWidgetController {

    public CarpoolWidgetService service;

    public CarpoolWidgetController (CarpoolWidgetService service){
        this.service = service;
    }

    @PostMapping("/")
    public EventDetailDto createCarpoolWidget(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody CarpoolCreateCommand createCommand
    ) {
        Event event = service.createCarpoolWidget(principal, eventId, createCommand);
        return service.mapEventToEventDetailDto(principal, event);
    }

    @PostMapping("/{widgetId}/cars")
    public CarpoolWidgetDto addCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody @Valid CarAddCommand addCommand
    ) {
        return service.addCar(principal, eventId, widgetId, addCommand);
    }

    @PatchMapping("/{widgetId}/cars/{carId}")
    public CarpoolWidgetDto updateCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @RequestBody @Valid CarUpdateCommand updateCommand
    ) {
        return service.updateCar(principal, eventId, widgetId, carId, updateCommand);
    }

    @DeleteMapping("/{widgetId}/cars/{carId}")
    public CarpoolWidgetDto removeCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId
    ) {
        return service.removeCar(principal, eventId, widgetId, carId);
    }

    @PostMapping("/{widgetId}/cars/{carId}")
    public CarpoolWidgetDto addRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @RequestBody RiderAddCommand addCommand
    ) {
        return service.addRider(principal, eventId, widgetId,carId, addCommand);
    }

    @DeleteMapping("/{widgetId}/cars/{carId}/riders/{riderId}")
    public CarpoolWidgetDto removeRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @PathVariable String riderId
    ) {
        return service.removeRider(principal, eventId, widgetId, carId, riderId);
    }

}
