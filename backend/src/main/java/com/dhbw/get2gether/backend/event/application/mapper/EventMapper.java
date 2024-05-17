package com.dhbw.get2gether.backend.event.application.mapper;

import com.dhbw.get2gether.backend.event.model.*;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.widget.application.mapper.WidgetMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = WidgetMapper.class)
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

    default EventDetailDto toEventDetailDto(Event event, @Context List<SimpleUserDto> participants) {
        return _toEventDetailDto(event, participants, participants);
    }

    /**
     * _Internal usage only!_
     *
     * This is a helper method to map an Event to an EventDro with a list of participants while also providing the participants list as mapping context.
     * Mapstruct will use the {@code WidgetMapper} to map Widgets to IWidgets.
     */
    EventDetailDto _toEventDetailDto(Event event, List<SimpleUserDto> participants, @Context List<SimpleUserDto> participantsContext);
}
