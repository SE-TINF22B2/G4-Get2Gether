package com.dhbw.get2gether.backend.user.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Guest extends User {

    private final boolean isGuest = true;

    public Guest(String id, LocalDateTime creationDate) {
        super(id, creationDate, "Guest", "", "", getProfilePictureUrl(id));
    }

    private static String getProfilePictureUrl(String hash) {
        // see here for gravatar default image types: https://docs.gravatar.com/general/images/
        return "https://www.gravatar.com/avatar/%s?d=retro".formatted(hash);
    }
}
