package hh.crossreview.resource;

import hh.crossreview.dto.user.SignInRequestDto;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.service.AuthenticationService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;

@Named
@Path("/auth")
@Singleton
public class AuthenticationResource {

  private final AuthenticationService authenticationService;

  @Inject
  public AuthenticationResource(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @POST
  @Path("/sign-up")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity<?> signUp(SignUpRequestDto request) {
    return authenticationService.createNewUser(request);
  }

  @POST
  @Path("/sign-in")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity<?> signIn(SignInRequestDto request) {
    return authenticationService.createAuthToken(request);
  }
}
