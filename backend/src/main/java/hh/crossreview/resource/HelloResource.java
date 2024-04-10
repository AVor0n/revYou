package hh.crossreview.resource;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@Tag(name = "Hello")
@SecurityRequirement(name = "bearerAuth")
public class HelloResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHello() {
    return "Hello, World!";
  }

}
