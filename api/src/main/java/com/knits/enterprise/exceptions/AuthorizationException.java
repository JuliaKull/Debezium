package com.knits.enterprise.exceptions;


public class AuthorizationException extends UserException {

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(Exception e) {
        super(e);
    }
}
