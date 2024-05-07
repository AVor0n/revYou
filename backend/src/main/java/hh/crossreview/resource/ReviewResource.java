package hh.crossreview.resource;

import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.dto.review.ReviewResolutionDto;
import hh.crossreview.dto.review.ReviewWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.User;
import hh.crossreview.service.HomeworkService;
import hh.crossreview.service.ReviewService;
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
@Path("/homeworks/{homeworkId}/")
@Singleton
@Tag(name = "Reviews")
@ApiResponse(
        responseCode = "403",
        description = "Forbidden request",
        content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@ApiResponse(
        responseCode = "404",
        description = "Not found",
        content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@SecurityRequirement(name = "bearerAuth")
public class ReviewResource {
  private final ReviewService reviewService;

  private final UserService userService;

  private final HomeworkService homeworkService;


  @Inject
  public ReviewResource(ReviewService reviewService, UserService userService, HomeworkService homeworkService) {
    this.reviewService = reviewService;
    this.userService = userService;
    this.homeworkService = homeworkService;
  }

  @POST
  @Path("/create-review")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "201",
          description = "Created")
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response createReview(
          @PathParam("homeworkId") Integer homeworkId,
          @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    reviewService.createReview(homework, user);
    return  Response
            .status(Response.Status.CREATED)
            .build();
  }

  @GET
  @Path("/my-reviews")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = ReviewWrapperDto.class)))
  public Response getMyReviews(
          @PathParam("homeworkId") Integer homeworkId,
          @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    ReviewWrapperDto reviewWrapperDto = reviewService.getMyReviews(homework, user);
    return Response
            .status(Response.Status.OK)
            .entity(reviewWrapperDto)
            .build();
  }


  @GET
  @Path("/reviews-to-do")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = ReviewWrapperDto.class)))
  public Response getReviewsToDo(
          @PathParam("homeworkId") Integer homeworkId,
          @Context SecurityContext securityContext
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    ReviewWrapperDto reviewWrapperDto = reviewService.getReviewsToDo(homework, user);
    return Response
            .status(Response.Status.OK)
            .entity(reviewWrapperDto)
            .build();
  }

  @POST
  @Path("/reviews/{reviewId}/start")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "201",
          description = "Created")
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response startReview(
          @PathParam("homeworkId") Integer homeworkId,
          @PathParam("reviewId") Integer reviewId,
          @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    Homework homework = homeworkService.getHomeworkEntity(homeworkId);
    reviewService.startReview(homework, user, reviewId);
    return  Response
            .status(Response.Status.CREATED)
            .build();
  }

  @PATCH
  @Path("/reviews/{reviewId}/resolution")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "201",
          description = "Created")
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response addReviewResolution(
          ReviewResolutionDto reviewResolutionDto,
          @PathParam("reviewId") Integer reviewId,
          @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    reviewService.addReviewResolution(user, reviewId, reviewResolutionDto);
    return  Response
            .status(Response.Status.CREATED)
            .build();
  }

}
