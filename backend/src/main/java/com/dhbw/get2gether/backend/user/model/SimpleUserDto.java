package com.dhbw.get2gether.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class SimpleUserDto {
    private final String id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
}
