package com.dhbw.get2gether.backend.event.adapter.in;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.event.model.EventUpdateCommand;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/")
    public Event createEvent(
            @AuthenticationPrincipal OAuth2User principal, @RequestBody EventCreateCommand eventCreateCommand) {
        return eventService.createEvent(principal, eventCreateCommand);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/own")
    public List<Event> getOwnEvents(@AuthenticationPrincipal OAuth2User principal) {
        return eventService.getAllEventsFromUser(principal);
    }

    @GetMapping("/{eventId}/generateInvitationLink")
    public Event generateInvitationLink(@AuthenticationPrincipal OAuth2User principal, @PathVariable String eventId) {
        return eventService.generateInvitationLink(principal, eventId);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEventById(@AuthenticationPrincipal OAuth2User principal, @PathVariable String eventId) {
        eventService.deleteEventById(principal, eventId);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody EventUpdateCommand eventUpdateCommand) {
        return eventService.updateEvent(principal, eventId, eventUpdateCommand);
    }
}
