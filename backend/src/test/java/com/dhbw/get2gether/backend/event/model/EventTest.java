package com.dhbw.get2gether.backend.event.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void shouldAddParticipant() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("other")))
                .build();

        // when
        event.addParticipant("new");

        // then
        assertThat(event.getParticipantIds()).contains("new");
    }

    @Test
    void shouldAddParticipantIfHasLeft() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("other")))
                .leftParticipantIds(new ArrayList<>(List.of("test")))
                .build();

        // when
        event.addParticipant("test");

        // then
        assertThat(event.getParticipantIds()).contains("test");
        assertThat(event.getLeftParticipantIds()).doesNotContain("test");
    }

    @Test
    void shouldNotAddParticipantIfAlreadyPresent() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("test")))
                .build();

        // when
        // then
        assertThatThrownBy(() -> event.addParticipant("test"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldLeaveEvent() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("test")))
                .build();

        // when
        event.leaveEvent("test");

        // then
        assertThat(event.getParticipantIds()).doesNotContain("test");
        assertThat(event.getLeftParticipantIds()).contains("test");
    }

    @Test
    void shouldNotLeaveEventIfNotParticipant() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("other")))
                .build();

        // when
        // then
        assertThatThrownBy(() -> event.leaveEvent("test"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotLeaveEventIfAlreadyLeft() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("other")))
                .leftParticipantIds(new ArrayList<>(List.of("test")))
                .build();

        // when
        // then
        assertThatThrownBy(() -> event.leaveEvent("test"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldGetParticipantsAndLeftParticipants() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("present")))
                .leftParticipantIds(new ArrayList<>(List.of("left")))
                .build();

        // when
        List<String> allParticipants = event.getParticipantsAndLeftParticipants();

        // then
        assertThat(allParticipants).contains("present", "left");
    }

    @Test
    void shouldOnlyGetParticipants() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("present")))
                .leftParticipantIds(new ArrayList<>(List.of("left")))
                .build();

        // when
        List<String> participants = event.getParticipantIds();

        // then
        assertThat(participants).contains("present");
        assertThat(participants).doesNotContain("left");
    }

    @Test
    void shouldOnlyGetLeftParticipants() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("present")))
                .leftParticipantIds(new ArrayList<>(List.of("left")))
                .build();

        // when
        List<String> participants = event.getLeftParticipantIds();

        // then
        assertThat(participants).contains("left");
        assertThat(participants).doesNotContain("present");
    }

    @Test
    void shouldHasUserLeftEvent() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("present")))
                .leftParticipantIds(new ArrayList<>(List.of("left")))
                .build();

        // when
        boolean hasLeft = event.hasUserLeftEvent("left");

        // then
        assertThat(hasLeft).isTrue();
    }

    @Test
    void shouldNotHasUserLeftEvent() {
        // given
        Event event = Event.builder()
                .participantIds(new ArrayList<>(List.of("present")))
                .leftParticipantIds(new ArrayList<>(List.of("left")))
                .build();

        // when
        boolean hasLeft = event.hasUserLeftEvent("present");

        // then
        assertThat(hasLeft).isFalse();
    }
}