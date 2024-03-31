package hh.crossreview.resource;

import hh.crossreview.dto.homework.GetAllHomeworksWrapper;
import hh.crossreview.service.HomeworkService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
  public GetAllHomeworksWrapper getHomeworks() {
    return homeworkService.getHomeworks();
  }

}
