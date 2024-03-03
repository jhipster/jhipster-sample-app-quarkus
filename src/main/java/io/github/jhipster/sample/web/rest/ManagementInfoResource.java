package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.service.ManagementInfoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/management/info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ManagementInfoResource {

    private final ManagementInfoService managementInfoService;

    @Inject
    public ManagementInfoResource(ManagementInfoService managementInfoService) {
        this.managementInfoService = managementInfoService;
    }

    @GET
    public Response info() {
        return Response.ok(managementInfoService.getManagementInfo()).build();
    }
}
