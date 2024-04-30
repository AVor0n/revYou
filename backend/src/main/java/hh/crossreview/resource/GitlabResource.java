package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.gitlab.DiffsWrapperDto;
import hh.crossreview.external.gitlab.service.GitlabService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
@Path("/gitlab")
@Singleton
@Tag(name = "Gitlab")
@ApiResponse(
    responseCode = "403",
    description = "Forbidden request",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
)
@SecurityRequirement(name = "bearerAuth")
public class GitlabResource {

  private final GitlabService gitlabService;

  @Inject
  public GitlabResource(GitlabService gitlabService) {
    this.gitlabService = gitlabService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/projects/{projectId}/repository/compare")
  public Response getDiffs(
      @PathParam("projectId") Integer projectId,
      @QueryParam("from") String from,
      @QueryParam("to") String to) {
    DiffsWrapperDto diffsWrapperDto = gitlabService.retrieveDiffs(projectId, from, to);
    return Response
        .status(Response.Status.OK)
        .entity(diffsWrapperDto)
        .build();
  }

}
