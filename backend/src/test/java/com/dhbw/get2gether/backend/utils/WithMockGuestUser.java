package com.dhbw.get2gether.backend.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockGuestUserSecurityContextFactory.class)
public @interface WithMockGuestUser {}
