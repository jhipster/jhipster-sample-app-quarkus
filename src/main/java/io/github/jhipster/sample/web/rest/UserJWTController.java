package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.security.jwt.TokenProvider;
import io.github.jhipster.sample.service.AuthenticationService;
import io.github.jhipster.sample.web.rest.vm.LoginVM;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller to authenticate users.
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    final AuthenticationService authenticationService;

    final TokenProvider tokenProvider;

    @Inject
    public UserJWTController(AuthenticationService authenticationService, TokenProvider tokenProvider) {
        this.authenticationService = authenticationService;
        this.tokenProvider = tokenProvider;
    }

    @POST
    @Path("/authenticate")
    @PermitAll
    public Response authorize(@Valid LoginVM loginVM) {
        try {
            QuarkusSecurityIdentity identity = authenticationService.authenticate(loginVM.username, loginVM.password);
            boolean rememberMe = (loginVM.rememberMe == null) ? false : loginVM.rememberMe;
            String jwt = tokenProvider.createToken(identity, rememberMe);
            return Response.ok().entity(new JWTToken(jwt)).header("Authorization", "Bearer " + jwt).build();
        } catch (SecurityException e) {
            throw new UnauthorizedException();
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    @RegisterForReflection
    public static class JWTToken {

        @JsonbProperty("id_token")
        public String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
