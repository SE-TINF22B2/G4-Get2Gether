package com.dhbw.get2gether.backend.user.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class User {
    @Id
    private final String id;

    private final LocalDateTime creationDate;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
