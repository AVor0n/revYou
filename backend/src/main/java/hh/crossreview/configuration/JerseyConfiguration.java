package hh.crossreview.configuration;

import hh.crossreview.exceptionmapper.GenericExceptionMapper;
import hh.crossreview.resource.AuthenticationResource;
import hh.crossreview.resource.HelloResource;
import hh.crossreview.resource.HomeworkResource;
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
    register(AuthenticationResource.class);
    register(GenericExceptionMapper.class);
  }

}
