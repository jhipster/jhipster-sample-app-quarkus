package com.mycompany.myapp.web.rest.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class EmailNotFoundException extends WebApplicationException {

    public EmailNotFoundException() {
        super(Response.status(BAD_REQUEST).entity("Email address not registered").build());
    }
}
