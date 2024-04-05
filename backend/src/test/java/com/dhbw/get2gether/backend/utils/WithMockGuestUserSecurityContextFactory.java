package com.dhbw.get2gether.backend.utils;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.authentication.GuestAuthenticationToken;
import java.time.LocalDateTime;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockGuestUserSecurityContextFactory implements WithSecurityContextFactory<WithMockGuestUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockGuestUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        GuestAuthenticationPrincipal principal = new GuestAuthenticationPrincipal("test", LocalDateTime.now());
        Authentication auth = new GuestAuthenticationToken(
                principal, Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST")));
        auth.setAuthenticated(true);

        context.setAuthentication(auth);
        return context;
    }
}
