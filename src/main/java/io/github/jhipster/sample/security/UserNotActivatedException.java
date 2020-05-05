package io.github.jhipster.sample.security;

import javax.ws.rs.NotAuthorizedException;

public class UserNotActivatedException extends NotAuthorizedException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
