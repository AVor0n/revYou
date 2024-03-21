package com.example.backend.configuration;

import com.example.backend.exceptionmappers.GenericExceptionMapper;
import com.example.backend.resources.HelloResource;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {

    @PostConstruct
    public void init() {
        register(HelloResource.class);
        register(GenericExceptionMapper.class);
    }

}
