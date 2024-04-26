package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPatchDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.User;
import hh.crossreview.service.HomeworkService;
import hh.crossreview.service.SolutionService;
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
@Path("/homeworks/{homeworkId}/solutions")
@Singleton
@Tag(name = "Solutions")
@ApiResponse(
    responseCode = "403",
    description = "Forbidden request",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@ApiResponse(
    responseCode = "404",
    description = "Not found",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@SecurityRequirement(name = "bearerAuth")
public class SolutionResource {

  private final SolutionService solutionService;
  private final HomeworkService homeworkService;
  private final UserService userService;

  @Inject
  public SolutionResource(SolutionService solutionService, HomeworkService homeworkService, UserService userService) {
    this.solutionService = solutionService;
    this.homeworkService = homeworkService;
    this.userService = userService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "You can create a solution for a specific homework only once")
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = SolutionDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response createSolution(
      @Valid SolutionPostDto solutionPostDto,
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    SolutionDto solutionDto = solutionService.createSolution(
        solutionPostDto,
        homework,
        user
    );
    return Response
        .status(Response.Status.CREATED)
        .entity(solutionDto)
        .build();
  }

  @PATCH
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updating the solution branch is only available if no attempts have been made")
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = SolutionDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response updateSolution(
      @Valid SolutionPatchDto solutionPatchDto,
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    SolutionDto solutionDto = solutionService.updateSolution(
        solutionPatchDto,
        homework,
        user
    );
    return Response
        .status(Response.Status.OK)
        .entity(solutionDto)
        .build();
  }

  @GET
  @Path("/student-solution")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Available only for students")
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = SolutionDto.class)))
  public Response readSolution(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    SolutionDto solutionDto = solutionService.getSolution(
        homework,
        user
    );
    return Response
        .status(Response.Status.OK)
        .entity(solutionDto)
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Available only for teachers")
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = SolutionsWrapperDto.class)))
  public Response readSolutions(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    SolutionsWrapperDto solutionsWrapperDto = solutionService.getSolutions(
        homework,
        user
    );
    return Response
        .status(Response.Status.OK)
        .entity(solutionsWrapperDto)
        .build();
  }

  @POST
  @Path("/attempts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = SolutionAttemptDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response createSolutionAttempt(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    SolutionAttemptDto solutionAttemptDto =  solutionService.createSolutionAttempt(
        homework,
        user
    );
    return Response
        .status(Response.Status.CREATED)
        .entity(solutionAttemptDto)
        .build();
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation"
  )
  public Response deleteSolution(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    solutionService.deleteSolution(homework, user);
    return Response
        .status(Response.Status.OK)
        .build();
  }

}
