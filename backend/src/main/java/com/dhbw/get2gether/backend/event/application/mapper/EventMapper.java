package com.dhbw.get2gether.backend.event.application.mapper;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.widget.model.Widget;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invitationLink", ignore = true)
    @Mapping(target = "widgets", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "participantIds", ignore = true)
    Event toEvent(EventCreateCommand eventCreateCommand);

    @AfterMapping
    default void toEvent(@MappingTarget Event.EventBuilder builder) {
        builder.id(UUID.randomUUID().toString());
        builder.invitationLink("");
        builder.widgets(new ArrayList<Widget>());
        builder.participantIds(new ArrayList<String>());
    }
}
