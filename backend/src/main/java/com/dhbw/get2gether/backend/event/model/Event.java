package com.dhbw.get2gether.backend.event.model;

import com.dhbw.get2gether.backend.widget.model.Widget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Event {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private String invitationLink;
    private String creatorId;
    private List<String> participantIds;
    private List<Widget> widgets;

    public void addParticipant(String participantId) {
        participantIds.add(participantId);
    }

    public void removeParticipant(String participantId) {
        participantIds.remove(participantId);
    }
}
