package io.github.jhipster.sample.web.rest.errors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EmailNotFoundException extends BadRequestAlertException {

    public EmailNotFoundException() {
        super("Email address not registered", "userManagement", "emailnotfound");
    }
}
