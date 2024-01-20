package com.dhbw.get2gether.backend.event.adapter.in;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    @PostMapping("/create")
    public void createEvent(@AuthenticationPrincipal OAuth2User principal, @RequestBody EventCreateCommand eventCreateCommand) {
        eventService.createEvent(principal, eventCreateCommand);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/own")
    public List<Event> getOwnEvents(@AuthenticationPrincipal OAuth2User principal) {
        return eventService.getAllEventsFromUser(principal);
    }
}
