package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.user.info.UserInfoDto;
import hh.crossreview.dto.user.update.PasswordPatchDto;
import hh.crossreview.dto.user.update.UserPatchDto;
import hh.crossreview.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jdk.jfr.Name;

@Named
@Singleton
@Tag(name = "Users")
@Path("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserResource {

  private final UserService userService;

  @Inject
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @PATCH
  @Path("/update-profile")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update profile data (username, name, surname)")
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = UserInfoDto.class))
  )
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response updateProfile(
      @Valid UserPatchDto userPatchDto,
      @Context SecurityContext securityContext
  ) {
    UserInfoDto userInfoDto = userService.updateProfile(securityContext.getUserPrincipal(), userPatchDto);
    return Response
        .status(Response.Status.OK)
        .entity(userInfoDto)
        .build();
  }

  @PATCH
  @Path("/update-password")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update profile password")
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation")
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response updatePassword(
      @Valid PasswordPatchDto passwordPatchDto,
      @Context SecurityContext securityContext
  ) {
    userService.updatePassword(securityContext.getUserPrincipal(), passwordPatchDto);
    return Response
        .status(Response.Status.OK)
        .build();
  }

}
