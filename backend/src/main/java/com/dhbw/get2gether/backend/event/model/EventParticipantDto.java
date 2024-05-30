package com.dhbw.get2gether.backend.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventParticipantDto {
    private final String id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private boolean hasLeft;
}
