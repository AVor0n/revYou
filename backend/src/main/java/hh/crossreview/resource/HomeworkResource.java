package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPatchDto;
import hh.crossreview.dto.homework.HomeworkPostDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.entity.User;
import hh.crossreview.external.gitlab.service.GitlabService;
import hh.crossreview.service.HomeworkService;
import hh.crossreview.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Named
@Path("/homeworks")
@Singleton
@Tag(name = "Homeworks")
@ApiResponse(
    responseCode = "403",
    description = "Forbidden request",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
)
@SecurityRequirement(name = "bearerAuth")
public class HomeworkResource {

  private final HomeworkService homeworkService;
  private final UserService userService;
  private final GitlabService gitlabService;

  @Inject
  public HomeworkResource(
      HomeworkService homeworkService,
      UserService userService,
      GitlabService gitlabService
  ) {
    this.homeworkService = homeworkService;
    this.userService = userService;
    this.gitlabService = gitlabService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = HomeworksWrapperDto.class))
  )
  public Response getHomeworks(@Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(user);
    return Response
        .status(Response.Status.OK)
        .entity(homeworksWrapperDto)
        .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = HomeworkPostResponseDto.class))
  )
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class))
  )
  public Response createHomework(
      HomeworkPostDto homeworkPostDto,
      @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    String commitId = gitlabService.validateHomeworkBranchLink(homeworkPostDto.getRepositoryLink());
    HomeworkPostResponseDto homeworkPostResponseDto = homeworkService.createHomework(
        homeworkPostDto,
        user,
        commitId
    );
    return Response
        .status(Response.Status.CREATED)
        .entity(homeworkPostResponseDto)
        .build();
  }

  @GET
  @Path("{homeworkId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = HomeworkDto.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = @Content(schema = @Schema(implementation = ExceptionDto.class))
  )
  public Response getHomework(@PathParam("homeworkId") Integer homeworkId) {
    HomeworkDto homeworkDto = homeworkService.getHomework(homeworkId);
    return Response
        .status(Response.Status.OK)
        .entity(homeworkDto)
        .build();
  }

  @PATCH
  @Path("{homeworkId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation"
  )
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "Not found",
      content = @Content(schema = @Schema(implementation = ExceptionDto.class))
  )
  public Response updateHomework(
      @PathParam("homeworkId") Integer homeworkId,
      HomeworkPatchDto homeworkPatchDto,
      @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    homeworkService.updateHomework(
        homeworkId,
        homeworkPatchDto,
        user
    );
    return Response
        .status(Response.Status.OK)
        .build();
  }

  @DELETE
  @Path("{homeworkId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "204",
      description = "No content"
  )
  public Response deleteHomework(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    homeworkService.deleteHomework(
        homeworkId,
        user
    );
    return Response
        .status(Response.Status.NO_CONTENT)
        .build();
  }

}
