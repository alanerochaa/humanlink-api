package com.humanlink.controller;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class RootController {

    @GET
    public String hello() {
        return "API HumanLink está no ar 🚀";
    }
}
