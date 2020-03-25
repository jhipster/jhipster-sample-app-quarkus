package com.mycompany.myapp.security;

import javax.ws.rs.NotAuthorizedException;

public class UserNotActivatedException extends NotAuthorizedException {
    public UserNotActivatedException(String message) {
        super(message);
    }
}
