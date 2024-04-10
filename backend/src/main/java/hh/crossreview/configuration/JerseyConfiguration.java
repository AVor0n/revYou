package hh.crossreview.configuration;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
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
    configureSwagger();
  }

  private void configureSwagger() {
    SwaggerConfiguration oasConfig = createSwaggerConfiguration();
    try {
      new JaxrsOpenApiContextBuilder<>()
          .openApiConfiguration(oasConfig)
          .buildContext(true);
    } catch (OpenApiConfigurationException e) {
      throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

  private SwaggerConfiguration createSwaggerConfiguration() {
    return new SwaggerConfiguration()
        .openAPI(new OpenAPI()
            .info(new Info()
                .title("Cross Review OpenAPI")
                .version("0.0")
                .description("Specification for cross-review service"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))));
  }

}
