package com.dhbw.get2gether.backend.event.model;

import com.dhbw.get2gether.backend.widget.model.Widget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Event {
    @Id
    private String id;

    private final LocalDateTime creationDate;
    private String name;
    private String description;
    private EventLocation location;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private String invitationLink;
    private final String creatorId;
    @Builder.Default
    private final List<String> participantIds = new ArrayList<>();
    @Builder.Default
    private final List<String> leftParticipantIds = new ArrayList<>();
    @Builder.Default
    private final List<Widget> widgets = new ArrayList<>();

    public void addParticipant(String participantId) {
        if (participantIds.contains(participantId)) {
            throw new IllegalArgumentException("ID " + participantId + " is already a participant of this event.");
        }
        leftParticipantIds.remove(participantId);
        participantIds.add(participantId);
    }

    public void leaveEvent(String participantId) {
        if (leftParticipantIds.contains(participantId)) {
            throw new IllegalArgumentException("Participant " + participantId + " has already left this event.");
        }
        if (!participantIds.contains(participantId)) {
            throw new IllegalArgumentException("Participant " + participantId + " is not a participant of this event.");
        }
        leftParticipantIds.add(participantId);
        participantIds.remove(participantId);
    }

    public List<String> getParticipantsAndLeftParticipants() {
        return Stream.concat(getParticipantIds().stream(), getLeftParticipantIds().stream()).toList();
    }

    public boolean hasUserLeftEvent(String userId) {
        return getLeftParticipantIds().contains(userId);
    }
}
