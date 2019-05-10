package com.galua.onlinestore.customerservice.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }

    public InvalidJwtAuthenticationException(String e, Throwable t) {
        super(e, t);
    }
}