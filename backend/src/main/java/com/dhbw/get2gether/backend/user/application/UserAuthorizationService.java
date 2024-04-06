package com.dhbw.get2gether.backend.user.application;

import com.dhbw.get2gether.backend.user.model.User;
import java.util.Objects;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService {

    /**
     * Checks if the principal is allowed to access the target user object by comparing the email address.
     *
     * @param principal        The principal requesting the resource.
     * @param targetUserObject The target user object.
     * @return {@code true} if the principal is a {@link OAuth2User} and the email address is equal to the
     * target user objects email, {@code false} otherwise.
     */
    public boolean isAuthorized(AuthenticatedPrincipal principal, User targetUserObject) {
        if (principal instanceof OAuth2User oAuth2User)
            return Objects.equals(oAuth2User.getAttribute("email"), targetUserObject.getEmail());
        return false;
    }
}
