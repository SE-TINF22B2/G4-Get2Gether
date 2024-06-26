package com.dhbw.get2gether.backend.event.model;

import com.dhbw.get2gether.backend.widget.model.IWidget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventDetailDto {
    private String id;

    private final LocalDateTime creationDate;
    private String name;
    private String description;
    private EventLocation location;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private String invitationLink;
    private final String creatorId;
    private final List<EventParticipantDto> participants;
    private final List<IWidget> widgets;
}
