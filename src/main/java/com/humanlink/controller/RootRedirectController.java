package com.humanlink.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/")
public class RootRedirectController {

    @GET
    public Response redirectToSwagger() {
        return Response.status(Response.Status.FOUND)
                .location(UriBuilder.fromPath("/swagger-ui").build())
                .build();
    }
}
