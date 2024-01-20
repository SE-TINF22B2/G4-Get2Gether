package com.dhbw.get2gether.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class CreateUserCommand {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
