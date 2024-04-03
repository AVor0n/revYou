package hh.crossreview.resource;

import hh.crossreview.dto.homework.AllHomeworksWrapperDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.service.HomeworkService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
@Path("/homeworks")
@Singleton
public class HomeworkResource {

  private final HomeworkService homeworkService;

  @Inject
  public HomeworkResource(HomeworkService homeworkService) {
    this.homeworkService = homeworkService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHomeworks() {
    AllHomeworksWrapperDto allHomeworksWrapperDto = homeworkService.getHomeworks();
    return Response
        .status(Response.Status.OK)
        .entity(allHomeworksWrapperDto)
        .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createHomework(HomeworkDto homeworkDto) {
    PostHomeworkResponseDto postHomeworkResponseDto = homeworkService.createHomework(homeworkDto);
    return Response
        .status(Response.Status.CREATED)
        .entity(postHomeworkResponseDto)
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
  public Response updateHomework(
      @PathParam("homeworkId") Integer homeworkId,
      HomeworkDto homeworkDto) {
    homeworkService.updateHomework(homeworkId, homeworkDto);
    return Response
        .status(Response.Status.OK)
        .build();
  }

  @DELETE
  @Path("{homeworkId}")
  public Response deleteHomework(@PathParam("homeworkId") Integer homeworkId) {
    homeworkService.deleteHomework(homeworkId);
    return Response
        .status(Response.Status.OK)
        .build();
  }

}
