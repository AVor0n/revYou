package com.example.backend.configuration;

import com.example.backend.exceptionmapper.GenericExceptionMapper;
import com.example.backend.resource.HelloResource;
import com.example.backend.resource.HomeworkResource;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

  @PostConstruct
  public void init() {
    register(HelloResource.class);
    register(HomeworkResource.class);
    register(GenericExceptionMapper.class);
  }

}
