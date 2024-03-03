package io.github.jhipster.sample.security;

import jakarta.ws.rs.NotAuthorizedException;

public class UserNotActivatedException extends NotAuthorizedException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
