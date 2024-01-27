package com.dhbw.get2gether.backend.authentication;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.AuthenticatedPrincipal;

@Getter
@AllArgsConstructor
public class GuestAuthenticationPrincipal implements AuthenticatedPrincipal {

    private final String id;
    private final LocalDateTime creationTime;
    private final Set<String> grantedEventIds = new HashSet<>();

    public Set<String> getGrantedEventIds() {
        return Collections.unmodifiableSet(grantedEventIds);
    }

    public void grantAccessToEvent(String id) {
        grantedEventIds.add(id);
    }

    @Override
    public String getName() {
        return "Guest " + id;
    }
}
