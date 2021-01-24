package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.service.ManagementInfoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response info(){
        return Response.ok(managementInfoService.getManagementInfo()).build();
    }
}
