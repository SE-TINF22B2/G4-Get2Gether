package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.user.application.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.List;

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


    public Event createEvent(SecurityContext securityContext, EventCreateCommand eventCreateCommand) {
//        User user = this.userService.findByEmail(securityContext.getAuthentication().

                securityContext.getAuthentication().getPrincipal();
          Event event = this.eventMapper.toEvent(eventCreateCommand);
          event.setCreatorId("");
        System.out.println(event.toString());
          return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
