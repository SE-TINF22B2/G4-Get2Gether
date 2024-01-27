package com.dhbw.get2gether.backend.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class GuestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final List<GrantedAuthority> authorities;

    public GuestAuthenticationFilter(AuthenticationManager authenticationManager, RequestMatcher requestMatcher) {
        super(new GuestAuthenticationRequestMatcher(requestMatcher), authenticationManager);
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST"));

        setSessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(List.of(
                new ChangeSessionIdAuthenticationStrategy(),
                new CsrfAuthenticationStrategy(new HttpSessionCsrfTokenRepository()))));
        setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        setAuthenticationSuccessHandler(
                (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    // do not redirect on successful authentication
                });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        GuestAuthenticationToken authRequest = createGuestAuthentication();
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        // continue the chain on successful authentication
        chain.doFilter(request, response);
    }

    private GuestAuthenticationToken createGuestAuthentication() {
        GuestAuthenticationPrincipal principal =
                new GuestAuthenticationPrincipal(UUID.randomUUID().toString(), LocalDateTime.now());
        return new GuestAuthenticationToken(principal, authorities);
    }
}
