package com.humanlink.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/")
public class RootRedirectController {

    @GET
    public Response redirectToSwagger() {
        return Response.status(Response.Status.FOUND)
                .location(java.net.URI.create("/swagger-ui"))
                .build();
    }
}
