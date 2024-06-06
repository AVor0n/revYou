package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.lecture.LectureDto;
import hh.crossreview.dto.lecture.LecturePatchDto;
import hh.crossreview.dto.lecture.LecturePostDto;
import hh.crossreview.dto.lecture.LecturePostResponseDto;
import hh.crossreview.dto.lecture.LectureWrapperDto;
import hh.crossreview.service.LectureService;
import hh.crossreview.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
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
@Singleton
@Tag(name = "Lecture")
@ApiResponse(
    responseCode = "403",
    description = "Forbidden request",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
)
@Path("/lectures")
public class LectureResource {
  private final LectureService lectureService;
  private final UserService userService;

  @Inject
  public LectureResource(LectureService lectureService, UserService userService) {
    this.lectureService = lectureService;
    this.userService = userService;
  }

  @GET
  @Path("{lectureId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = LectureDto.class)))
  public Response getLecture(@PathParam("lectureId") Integer lectureId) {
    var lectureDto = lectureService.getLecture(lectureId);
    return Response.status(Response.Status.OK).entity(lectureDto).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = LecturePostResponseDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response createLecture(@Valid LecturePostDto lecturePostDto, @Context SecurityContext securityContext) {
    var lector = userService.findByUserId(lecturePostDto.getLectorId());
    var admin = userService.findByPrincipal(securityContext.getUserPrincipal());
    var lectureResponse = lectureService.createLecture(lecturePostDto, lector, admin);
    return Response.status(Response.Status.CREATED).entity(lectureResponse).build();
  }

  @DELETE
  @Path("{lectureId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = LectureDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response deleteLecture(@PathParam("lectureId") Integer lectureId, @Context SecurityContext securityContext) {
    var lector = userService.findByPrincipal(securityContext.getUserPrincipal());
    lectureService.deleteLecture(lectureId, lector);
    return Response.status(Response.Status.OK).build();
  }

  @PATCH
  @Path("{lectureId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = LectureDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response patchLecture(
      @PathParam("lectureId") Integer lectureId,
      @Valid LecturePatchDto lecturePatchDto,
      @Context SecurityContext securityContext
  ) {
    var lector = userService.findByPrincipal(securityContext.getUserPrincipal());
    lectureService.updateLecture(lectureId, lecturePatchDto, lector);
    return Response.status(Response.Status.OK).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = LectureWrapperDto.class)))
  public Response getAllLectures(@Context SecurityContext securityContext) {
    var user = userService.findByPrincipal(securityContext.getUserPrincipal());
    var lectures = lectureService.getLectures(user);
    return Response.status(Response.Status.OK).entity(lectures).build();
  }
}
