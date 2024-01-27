package com.dhbw.get2gether.backend.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class GuestAuthenticationRequestMatcher implements RequestMatcher {

    private final RequestMatcher parentRequestMatcher;

    public GuestAuthenticationRequestMatcher(RequestMatcher parentRequestMatcher) {
        this.parentRequestMatcher = parentRequestMatcher;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (parentRequestMatcher != null && !parentRequestMatcher.matches(request)) return false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null;
    }
}
