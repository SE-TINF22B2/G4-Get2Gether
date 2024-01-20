package com.dhbw.get2gether.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateUserCommand {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
