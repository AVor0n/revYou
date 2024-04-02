package hh.crossreview.resource;

import hh.crossreview.dto.homework.AllHomeworksWrapperDto;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.homework.PostHomeworkResponseDto;
import hh.crossreview.service.HomeworkService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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

}
