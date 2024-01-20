package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.application.mapper.EventMapper;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {
    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventRepository eventRepository;

    public Event createEvent(SecurityContext securityContext, EventCreateCommand eventCreateCommand) {
//        User user = securityContext.getAuthentication().getPrincipal();
          Event event = eventMapper.toEvent(eventCreateCommand);
          event.setCreatorId("");
        System.out.println(event.toString());
          return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
