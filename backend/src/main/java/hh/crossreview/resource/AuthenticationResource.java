package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.user.auth.RefreshAccessTokenRequestDto;
import hh.crossreview.dto.user.auth.SignInRequestDto;
import hh.crossreview.dto.user.auth.SignInResponseDto;
import hh.crossreview.dto.user.auth.SignUpRequestDto;
import hh.crossreview.dto.user.info.UserDto;
import hh.crossreview.service.AuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
@Path("/auth")
@Singleton
@Tag(name = "Auth")
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
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = UserDto.class))
  )
  @ApiResponse(
      responseCode = "403",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionDto.class))
  )
  public Response signUp(@Valid SignUpRequestDto request) {
    return authenticationService.createNewUser(request);
  }

  @POST
  @Path("/sign-in")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = SignInResponseDto.class))
  )
  @ApiResponse(
      responseCode = "403",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionDto.class))
  )
  public Response signIn(@Valid SignInRequestDto request) {
    return authenticationService.createAuthToken(request);
  }

  @POST
  @Path("/refresh-access-token")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = SignInResponseDto.class))
  )
  @ApiResponse(
          responseCode = "403",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionDto.class))
  )
  public Response refreshAccessToken(@Valid RefreshAccessTokenRequestDto request) {
    return authenticationService.refreshToken(request);
  }
}
