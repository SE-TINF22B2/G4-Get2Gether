package com.dhbw.get2gether.backend;

import com.dhbw.get2gether.backend.user.model.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;

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
