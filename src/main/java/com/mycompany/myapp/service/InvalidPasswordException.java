package com.mycompany.myapp.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class InvalidPasswordException extends WebApplicationException {

    public InvalidPasswordException() {
        super(Response.status(BAD_REQUEST).entity("Incorrect Password").build());
    }
}
