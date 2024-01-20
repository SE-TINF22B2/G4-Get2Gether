package com.dhbw.get2gether.backend.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class User {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
