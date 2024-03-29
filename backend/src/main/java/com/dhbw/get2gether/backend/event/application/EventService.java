package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.event.model.EventUpdateCommand;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.env.Environment;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class EventService {
    private final Environment env;

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(
            EventMapper eventMapper, EventRepository eventRepository, UserService userService, Environment env) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.env = env;
    }

    public Event createEvent(OAuth2User principal, EventCreateCommand eventCreateCommand) {
        Optional<User> user = this.userService.findUserFromPrincipal(principal);
        return user.map(presentUser -> {
                    Event event = this.eventMapper.toEvent(eventCreateCommand).toBuilder()
                            .id(UUID.randomUUID().toString())
                            .creationDate(LocalDateTime.now())
                            .invitationLink("")
                            .widgets(new ArrayList<>())
                            .participantIds(new ArrayList<>())
                            .creatorId(presentUser.getId())
                            .build();
                    event.addParticipant(presentUser.getId());
                    return eventRepository.insert(event);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllEventsFromUser(OAuth2User principal) {
        return userService
                .findUserFromPrincipal(principal)
                .map(user -> eventRepository.findEventsByParticipantIdsContains(user.getId()))
                .orElse(List.of());
    }

    public void deleteEventById(OAuth2User principal, String eventId) {
        Event event = getEventIfUserIsParticipant(principal, eventId);
        eventRepository.delete(event);
    }

    public Event updateEvent(OAuth2User principal, String eventId, EventUpdateCommand eventUpdateCommand) {
        Event oldEvent = getEventIfUserIsParticipant(principal, eventId);
        Event newEvent = eventMapper.updateEvent(oldEvent, eventUpdateCommand);
        return eventRepository.save(newEvent);
    }

    public Event addParticipantToEvent(OAuth2User principal, String invitationLink) {
        Optional<Event> event = eventRepository.findByInvitationLink(invitationLink);
        return event.map(presentEvent -> {
                    Optional<User> user = this.userService.findUserFromPrincipal(principal);
                    return user.map(presentUser -> {
                                presentEvent.addParticipant(presentUser.getId());
                                return eventRepository.save(presentEvent);
                            })
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                })
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Event getEventById(String eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Event getEventIfUserIsParticipant(OAuth2User principal, String eventId) {
        User user = userService
                .findUserFromPrincipal(principal)
                .orElseThrow(() -> new IllegalArgumentException("User is not logged in"));
        Event event = getEventById(eventId);
        if (event.getParticipantIds().contains(user.getId())) {
            return event;
        } else {
            throw new IllegalArgumentException("User is not a participant of this event");
        }
    }

    public Event generateInvitationLink(OAuth2User principal, String eventId) {
        Event event = getEventIfUserIsParticipant(principal, eventId);
        event.setInvitationLink("invitation-" + UUID.randomUUID());
        return eventRepository.save(event);
    }

    public Optional<Event> getEventByInvitationLink(String invitationLink) {
        return eventRepository.findByInvitationLink(invitationLink);
    }

    public Optional<String> getRouteFromInvitationLink(AuthenticatedPrincipal principal, String invitationLink) {
        Optional<Event> event = getEventByInvitationLink(invitationLink);
        if (event.isPresent() && principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
            guestPrincipal.grantAccessToEvent(event.get().getId());
        }
        return event.map(presentEvent -> env.getProperty("frontend.url") + "/event/" + presentEvent.getId());
    }
}
