package com.dhbw.backend;

import com.dhbw.backend.user.model.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private final Token token;

    public AuthenticationToken(Token token) {
        super(null);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
