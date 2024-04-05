package com.dhbw.get2gether.backend.utils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        final String nameAttributeKey = "sub";
        Map<String, Object> defaultAttributes = new HashMap<>();
        defaultAttributes.put(nameAttributeKey, "user");
        defaultAttributes.put("email", "test@example.com");

        Set<GrantedAuthority> defaultAuthorities = new LinkedHashSet<>();
        defaultAuthorities.add(new OAuth2UserAuthority(defaultAttributes));
        defaultAuthorities.add(new OAuth2UserAuthority("ROLE_USER", defaultAttributes));
        for (String authority : customUser.authorities()) {
            defaultAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        OAuth2User principal = new DefaultOAuth2User(defaultAuthorities, defaultAttributes, nameAttributeKey);
        Authentication auth = new OAuth2AuthenticationToken(principal, principal.getAuthorities(), "test");
        context.setAuthentication(auth);
        return context;
    }
}
