package com.dhbw.get2gether.backend.event.application.mapper;

import com.dhbw.get2gether.backend.event.model.*;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "invitationLink", ignore = true)
    @Mapping(target = "widgets", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    Event toEvent(EventCreateCommand eventCreateCommand);

    EventOverviewDto toEventOverviewDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invitationLink", ignore = true)
    @Mapping(target = "widgets", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    Event updateEvent(@MappingTarget Event event, EventUpdateCommand eventUpdateCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "invitationLink", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    Event updateEvent(@MappingTarget Event event, EventWidgetUpdateCommand eventWidgetUpdateCommand);

    EventDetailDto toEventDetailDto(Event event, List<SimpleUserDto> participants);
}
