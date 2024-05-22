package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.*;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.user.model.User;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        User creator = userService.getUserByPrincipal(principal);
        Event event = this.eventMapper.toEvent(eventCreateCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .invitationLink("")
                .widgets(new ArrayList<>())
                .participantIds(new ArrayList<>())
                .creatorId(creator.getId())
                .build();
        event.addParticipant(creator.getId());
        return eventRepository.insert(event);
    }

    @PreAuthorize("hasRole('USER')")
    public List<EventOverviewDto> getAllEventsFromUser(AuthenticatedPrincipal principal) {
        // TODO: check if principal is guest and return granted events
        List<Event> userEvents = userService
                .findUserFromPrincipal(principal)
                .map(user -> eventRepository.findEventsByParticipantIdsContainsOrderByDateDesc(user.getId()))
                .orElse(List.of());
        return userEvents.stream().map(eventMapper::toEventOverviewDto).toList();
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
    public Event updateEventWidgets(AuthenticatedPrincipal principal, String eventId, EventWidgetUpdateCommand eventWidgetUpdateCommand) {
        Event oldEvent = getEventIfUserIsParticipant(principal, eventId);
        Event newEvent = eventMapper.updateEvent(oldEvent, eventWidgetUpdateCommand);
        return eventRepository.save(newEvent);
    }

    @PreAuthorize("hasRole('USER')")
    public Event generateInvitationLink(AuthenticatedPrincipal principal, String eventId) {
        Event event = getEventIfUserIsParticipant(principal, eventId);
        event.setInvitationLink("invitation-" + UUID.randomUUID());
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('GUEST')")
    public Optional<String> openEventFromInvitationLink(AuthenticatedPrincipal principal, String invitationLink) {
        Optional<Event> event = findEventByInvitationLink(invitationLink);
        return event.map(presentEvent -> {
            if (principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
                guestPrincipal.grantAccessToEvent(presentEvent.getId());
            } else if (principal instanceof OAuth2User) {
                // add user as participant
                addUserToParticipantsIfNotParticipating(principal, presentEvent);
            }
            return env.getProperty("frontend.url") + "/event/" + presentEvent.getId();
        });
    }

    public EventDetailDto mapEventToEventDetailDto(Optional<String> currentUserId, Event event) {
        List<SimpleUserDto> participantsDtos = userService.getSimpleUsersById(event.getParticipantIds());
        return eventMapper.toEventDetailDto(event, participantsDtos, currentUserId);
    }

    public EventDetailDto mapEventToEventDetailDto(AuthenticatedPrincipal principal, Event event) {
        Optional<String> userId = userService.findUserFromPrincipal(principal).map(User::getId);
        return mapEventToEventDetailDto(userId, event);
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

    private Event addUserToParticipantsIfNotParticipating(AuthenticatedPrincipal principal, Event event) {
        User user = userService.getUserByPrincipal(principal);
        if (event.getParticipantIds().contains(user.getId())) return event;
        event.addParticipant(user.getId());
        return eventRepository.save(event);
    }
}
