package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventService {
    private EventMapper eventMapper;
    private EventRepository eventRepository;
    private UserService userService;

    public EventService(EventMapper eventMapper, EventRepository eventRepository, UserService userService) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.userService = userService;
    }


    public Event createEvent( OAuth2User principal, EventCreateCommand eventCreateCommand) {
        Optional<User> user = this.userService.findUserFromPrincipal(principal);
        return user.map(presentUser -> {
            Event event = this.eventMapper.toEvent(eventCreateCommand);
            event.setCreatorId(presentUser.getId());
            return eventRepository.save(event);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllEventsFromUser(OAuth2User principal) {
        return userService.findUserFromPrincipal(principal)
                .map(user -> eventRepository.findAllByParticipantIdsContaining(user.getId()))
                .orElse(List.of());
    }

}
