package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.widget.application.CarpoolWidgetService;
import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolCreateCommand;
import com.dhbw.get2gether.backend.widget.model.carpool.CarpoolWidget;
import com.dhbw.get2gether.backend.widget.model.carpool.RiderAddCommand;
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
    @PostMapping("/{widgetId}/entries")
    public CarpoolWidget addRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody RiderAddCommand addCommand
    ) {
        return service.addRider(principal, eventId, widgetId, addCommand);
    }
    @DeleteMapping("/{widgetId}/riders/{riderId}")
    public CarpoolWidget removeRider(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String riderId
    ) {
        return service.removeRider(principal, eventId, widgetId, riderId);
    }

}
