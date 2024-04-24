package com.dhbw.get2gether.backend.user.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private final String id;

    private final LocalDateTime creationDate;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
    private Settings settings;
}
