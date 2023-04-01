package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.security.AuthoritiesConstants;
import io.github.jhipster.sample.service.UserService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST controller to manage authorities.
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorityResource {

    final UserService userService;

    @Inject
    public AuthorityResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GET
    @Path("/authorities")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }
}
