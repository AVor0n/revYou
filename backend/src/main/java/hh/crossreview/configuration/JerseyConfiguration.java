package hh.crossreview.configuration;

import hh.crossreview.exceptionmapper.BadRequestExceptionMapper;
import hh.crossreview.exceptionmapper.ConstraintViolationExceptionMapper;
import hh.crossreview.exceptionmapper.ForbiddenExceptionMapper;
import hh.crossreview.exceptionmapper.GenericExceptionMapper;
import hh.crossreview.exceptionmapper.NotFoundExceptionMapper;
import hh.crossreview.exceptionmapper.UnauthorizedExceptionMapper;
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
    register(BadRequestExceptionMapper.class);
    register(ForbiddenExceptionMapper.class);
    register(GenericExceptionMapper.class);
    register(NotFoundExceptionMapper.class);
    register(ConstraintViolationExceptionMapper.class);
    register(UnauthorizedExceptionMapper.class);
  }

}
