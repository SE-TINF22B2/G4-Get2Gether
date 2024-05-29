package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.widget.application.CarpoolWidgetService;
import com.dhbw.get2gether.backend.widget.model.carpool.*;
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
    public Event createCarpoolWidget(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody CarpoolCreateCommand createCommand
    ) {
        return service.createCarpoolWidget(principal, eventId, createCommand);
    }

    @PostMapping("/{widgetId}/cars")
    public CarWidgetDto addCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody CarAddCommand addCommand
    ) {
        return service.addCar(principal, eventId, widgetId, addCommand);
    }

    @PatchMapping("/{widgetId}/cars/{carId}")
    public CarWidgetDto updateCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @RequestBody CarUpdateCommand updateCommand
    ) {
        return service.updateCar(principal, eventId, widgetId, carId, updateCommand);
    }

    @DeleteMapping("/{widgetId}/cars/{carId}")
    public CarWidgetDto removeCar(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId
    ) {
        return service.removeCar(principal, eventId, widgetId, carId);
    }

    @PostMapping("/{widgetId}/cars/{carId}")
    public CarWidgetDto addRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @RequestBody RiderAddCommand addCommand
    ) {
        return service.addRider(principal, eventId, widgetId,carId, addCommand);
    }

    @DeleteMapping("/{widgetId}/cars/{carId}/riders/{riderId}")
    public CarWidgetDto removeRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String carId,
            @PathVariable String riderId
    ) {
        return service.removeRider(principal, eventId, widgetId, carId, riderId);
    }

}
