package com.example.backend.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String getHello() {
        return "Hello, World!";
    }

}
