package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.feedback.FeedbackDto;
import hh.crossreview.dto.feedback.FeedbackPostDto;
import hh.crossreview.dto.feedback.FeedbackWrapperDto;
import hh.crossreview.service.FeedbackService;
import hh.crossreview.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Named
@Path("/feedback")
@Tag(name = "Feedback")
@ApiResponse(
    responseCode = "403",
    description = "Forbidden request",
    content = @Content(schema = @Schema(implementation = ExceptionDto.class))
)
@Singleton
public class FeedbackResource {

  private final FeedbackService feedbackService;
  private final UserService userService;

  @Inject
  public FeedbackResource(FeedbackService feedbackService, UserService userService) {
    this.feedbackService = feedbackService;
    this.userService = userService;
  }

  @GET
  @Path("{feedbackId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = FeedbackDto.class)))
  public Response getFeedback(@PathParam("feedbackId") Integer feedbackId) {
    var feedbackDto = feedbackService.getFeedback(feedbackId);
    return Response.status(Response.Status.OK).entity(feedbackDto).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "200",
      description = "Successful operation",
      content = @Content(schema = @Schema(implementation = FeedbackWrapperDto.class)))
  public Response getFeedbacks() {
    var feedbacks = feedbackService.getFeedbacks();
    var feedbacksWrapperDto = new FeedbackWrapperDto(feedbacks);
    return Response.status(Response.Status.OK).entity(feedbacksWrapperDto).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
      responseCode = "201",
      description = "Created",
      content = @Content(schema = @Schema(implementation = FeedbackPostDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad request",
      content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response createFeedback(
      FeedbackPostDto feedbackPostDto,
      @Context SecurityContext securityContext
  ) {
    var user = userService.findByPrincipal(securityContext.getUserPrincipal());
    var feedback = feedbackService.createFeedback(feedbackPostDto, user);
    return Response.status(Response.Status.CREATED).entity(feedback).build();
  }
}
