package hh.crossreview.resource;

import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.HomeworkPostResponseDto;
import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.service.HomeworkService;
import hh.crossreview.utils.JwtTokenUtils;
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
public class HomeworkResource {

  private final HomeworkService homeworkService;
  private final JwtTokenUtils jwtTokenUtils;

  @Inject
  public HomeworkResource(HomeworkService homeworkService, JwtTokenUtils jwtTokenUtils) {
    this.homeworkService = homeworkService;
    this.jwtTokenUtils = jwtTokenUtils;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHomeworks(@Context SecurityContext securityContext) {
    HomeworksWrapperDto homeworksWrapperDto = homeworkService.getHomeworks(
        jwtTokenUtils.retrieveTokenFromContext(securityContext)
    );
    return Response
        .status(Response.Status.OK)
        .entity(homeworksWrapperDto)
        .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createHomework(
      HomeworkDto homeworkDto,
      @Context SecurityContext securityContext) {
    HomeworkPostResponseDto homeworkPostResponseDto = homeworkService.createHomework(
        homeworkDto,
        jwtTokenUtils.retrieveTokenFromContext(securityContext)
    );
    return Response
        .status(Response.Status.CREATED)
        .entity(homeworkPostResponseDto)
        .build();
  }

  @GET
  @Path("{homeworkId}")
  @Produces(MediaType.APPLICATION_JSON)
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
  public Response updateHomework(
      @PathParam("homeworkId") Integer homeworkId,
      HomeworkDto homeworkDto,
      @Context SecurityContext securityContext) {
    homeworkService.updateHomework(
        homeworkId,
        homeworkDto,
        jwtTokenUtils.retrieveTokenFromContext(securityContext)
    );
    return Response
        .status(Response.Status.OK)
        .build();
  }

  @DELETE
  @Path("{homeworkId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteHomework(
      @PathParam("homeworkId") Integer homeworkId,
      @Context SecurityContext securityContext) {
    homeworkService.deleteHomework(
        homeworkId,
        jwtTokenUtils.retrieveTokenFromContext(securityContext)
    );
    return Response
        .status(Response.Status.OK)
        .build();
  }

}
