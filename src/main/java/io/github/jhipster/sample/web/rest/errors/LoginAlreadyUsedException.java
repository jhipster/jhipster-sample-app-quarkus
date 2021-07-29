package io.github.jhipster.sample.web.rest.errors;

import java.net.URI;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    public static final URI TYPE = URI.create(ErrorConstants.PROBLEM_BASE_URL + "/login-already-used");

    public LoginAlreadyUsedException() {
        super(TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
