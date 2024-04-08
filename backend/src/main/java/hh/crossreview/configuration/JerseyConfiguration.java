package hh.crossreview.configuration;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

  @PostConstruct
  public void init() {
    packages(
        "hh.crossreview.exceptionmapper",
        "hh.crossreview.resource"
    );
    register(OpenApiResource.class);
  }

}
