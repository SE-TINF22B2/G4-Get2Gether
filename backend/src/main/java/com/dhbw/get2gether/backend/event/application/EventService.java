package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.event.model.EventUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final Environment env;

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserService userService;

    EventService(
            EventMapper eventMapper, EventRepository eventRepository, UserService userService, Environment env) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.env = env;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createEvent(AuthenticatedPrincipal principal, EventCreateCommand eventCreateCommand) {
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
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    public List<Event> getAllEventsFromUser(AuthenticatedPrincipal principal) {
        return userService
                .findUserFromPrincipal(principal)
                .map(user -> eventRepository.findEventsByParticipantIdsContains(user.getId()))
                .orElse(List.of());
    }

    @PreAuthorize("hasRole('GUEST')")
    public Event getSingleEvent(AuthenticatedPrincipal principal, String eventId) {
        if (principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
            if (!guestPrincipal.getGrantedEventIds().contains(eventId))
                throw new AccessDeniedException("Guest is not granted permission for this event");
            return getEventById(eventId);
        }
        return getEventIfUserIsParticipant(principal, eventId);
    }

    @PreAuthorize("hasRole('USER')")
    public void deleteEventById(AuthenticatedPrincipal principal, String eventId) {
        Event event = getEventIfUserIsParticipant(principal, eventId);
        User user = userService
                .findUserFromPrincipal(principal)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!event.getCreatorId().equals(user.getId()))
            throw new AccessDeniedException("Event can only be deleted by its creator.");
        eventRepository.delete(event);
    }

    @PreAuthorize("hasRole('USER')")
    public Event updateEvent(AuthenticatedPrincipal principal, String eventId, EventUpdateCommand eventUpdateCommand) {
        Event oldEvent = getEventIfUserIsParticipant(principal, eventId);
        Event newEvent = eventMapper.updateEvent(oldEvent, eventUpdateCommand);
        return eventRepository.save(newEvent);
    }

    @PreAuthorize("hasRole('USER')")
    public Event addParticipantToEvent(AuthenticatedPrincipal principal, String invitationLink) {
        Optional<Event> event = eventRepository.findByInvitationLink(invitationLink);
        return event.map(presentEvent -> {
                    Optional<User> user = this.userService.findUserFromPrincipal(principal);
                    return user.map(presentUser -> {
                                presentEvent.addParticipant(presentUser.getId());
                                return eventRepository.save(presentEvent);
                            })
                            .orElseThrow(() -> new EntityNotFoundException("User not found"));
                })
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    @PreAuthorize("hasRole('USER')")
    public Event generateInvitationLink(AuthenticatedPrincipal principal, String eventId) {
        Event event = getEventIfUserIsParticipant(principal, eventId);
        event.setInvitationLink("invitation-" + UUID.randomUUID());
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('GUEST')")
    public Optional<String> findRouteFromInvitationLink(AuthenticatedPrincipal principal, String invitationLink) {
        Optional<Event> event = findEventByInvitationLink(invitationLink);
        if (event.isPresent() && principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
            guestPrincipal.grantAccessToEvent(event.get().getId());
        }
        return event.map(presentEvent -> env.getProperty("frontend.url") + "/event/" + presentEvent.getId());
    }

    private Event getEventById(String eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    private Event getEventIfUserIsParticipant(AuthenticatedPrincipal principal, String eventId) {
        User user = userService
                .findUserFromPrincipal(principal)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Event event = getEventById(eventId);
        if (event.getParticipantIds().contains(user.getId())) {
            return event;
        } else {
            throw new AccessDeniedException("User is not a participant of this event");
        }
    }

    private Optional<Event> findEventByInvitationLink(String invitationLink) {
        return eventRepository.findByInvitationLink(invitationLink);
    }
}
