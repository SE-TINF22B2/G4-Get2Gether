package com.dhbw.get2gether.backend.event.application.mapper;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.event.model.EventUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invitationLink", ignore = true)
    @Mapping(target = "widgets", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    Event toEvent(EventCreateCommand eventCreateCommand);

    Event updateEvent(@MappingTarget Event event, EventUpdateCommand eventUpdateCommand);
}
