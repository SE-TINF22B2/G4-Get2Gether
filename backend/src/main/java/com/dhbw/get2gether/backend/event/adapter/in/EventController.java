package com.dhbw.get2gether.backend.event.adapter.in;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.*;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/")
    public EventDetailDto createEvent(
            @AuthenticationPrincipal OAuth2User principal, @RequestBody EventCreateCommand eventCreateCommand) {
        Event event = eventService.createEvent(principal, eventCreateCommand);
        return eventService.mapEventToEventDetailDto(principal, event);
    }

    @GetMapping("/own")
    public List<EventOverviewDto> getOwnEvents(@AuthenticationPrincipal OAuth2User principal) {
        return eventService.getAllEventsFromUser(principal);
    }

    @GetMapping("/{eventId}")
    public EventDetailDto getSingleEvent(
            @AuthenticationPrincipal AuthenticatedPrincipal principal, @PathVariable String eventId) {
        Event event = eventService.getSingleEvent(principal, eventId);
        return eventService.mapEventToEventDetailDto(principal, event);
    }

    @GetMapping("/{eventId}/generateInvitationLink")
    public EventDetailDto generateInvitationLink(@AuthenticationPrincipal OAuth2User principal, @PathVariable String eventId) {
        Event event = eventService.generateInvitationLink(principal, eventId);
        return eventService.mapEventToEventDetailDto(principal, event);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEventById(@AuthenticationPrincipal OAuth2User principal, @PathVariable String eventId) {
        eventService.deleteEventById(principal, eventId);
    }

    @PutMapping("/{eventId}")
    public EventDetailDto updateEvent(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody EventUpdateCommand eventUpdateCommand) {
        Event event = eventService.updateEvent(principal, eventId, eventUpdateCommand);
        return eventService.mapEventToEventDetailDto(principal, event);
    }
}
