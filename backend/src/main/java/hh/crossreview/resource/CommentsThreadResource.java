package hh.crossreview.resource;

import hh.crossreview.dto.comment.CommentDto;
import hh.crossreview.dto.comment.CommentPostDto;
import hh.crossreview.dto.commentsthread.CommentsThreadDto;
import hh.crossreview.dto.commentsthread.CommentsThreadPostDto;
import hh.crossreview.dto.commentsthread.CommentsThreadResolveDto;
import hh.crossreview.dto.commentsthread.CommentsThreadWrapperDto;
import hh.crossreview.dto.exception.ExceptionDto;
import hh.crossreview.dto.exception.ExceptionValidationDto;
import hh.crossreview.entity.User;
import hh.crossreview.service.CommentsThreadService;
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
@Path("/threads")
@Singleton
@Tag(name = "Threads")
@ApiResponse(
        responseCode = "403",
        description = "Forbidden request",
        content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@ApiResponse(
        responseCode = "404",
        description = "Not found",
        content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
@SecurityRequirement(name = "bearerAuth")
public class CommentsThreadResource {

  private final CommentsThreadService commentsThreadService;

  private final UserService userService;

  @Inject
  public CommentsThreadResource(CommentsThreadService commentsThreadService, UserService userService) {
    this.commentsThreadService = commentsThreadService;
    this.userService = userService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Starting thread with first comment. Available to review participants, admin and teacher")
  @ApiResponse(
          responseCode = "201",
          description = "Created",
          content = @Content(schema = @Schema(implementation = CommentsThreadDto.class))
  )
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response startThread(
          CommentsThreadPostDto commentsThreadPostDto,
          @Context SecurityContext securityContext) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    CommentsThreadDto commentsThreadDto = commentsThreadService.createCommentsThread(commentsThreadPostDto, user);
    return Response
            .status(Response.Status.CREATED)
            .entity(commentsThreadDto)
            .build();
  }

  @GET
  @Path("/review/{reviewId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all treads and comments by reviewId. Available to review participants, admin and teacher")
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = CommentsThreadWrapperDto.class)))
  public Response getAllThreads(
          @Context SecurityContext securityContext,
          @PathParam("reviewId") Integer reviewId
  ) {
    User user = userService.findByPrincipal(securityContext.getUserPrincipal());
    CommentsThreadWrapperDto commentsThreadWrapperDto = commentsThreadService.getAllThreads(user, reviewId);
    return Response
            .status(Response.Status.OK)
            .entity(commentsThreadWrapperDto)
            .build();
  }

  @POST
  @Path("/{threadId}/comments")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Add comment to thread. Available to review participants, admin and teacher")
  @ApiResponse(
          responseCode = "201",
          description = "Created",
          content = @Content(schema = @Schema(implementation = CommentDto.class))
  )
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class))
  )
  public Response addComment(
      CommentPostDto commentPostDto,
      @PathParam("threadId") Integer commentsThreadId,
      @Context SecurityContext securityContext
  ) {
    User author = userService.findByPrincipal(securityContext.getUserPrincipal());
    CommentDto commentDto = commentsThreadService.addComment(commentPostDto, commentsThreadId, author);
    return Response
            .status(Response.Status.CREATED)
            .entity(commentDto)
            .build();
  }

  @DELETE
  @Path("/comments/{commentId}")
  @Operation(summary = "Delete comment. Available to author of the comment and admin")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiResponse(
          responseCode = "204",
          description = "No content"
  )
  public Response deleteComment(
          @PathParam("commentId") Integer commentId,
          @Context SecurityContext securityContext
  ) {
    User author = userService.findByPrincipal(securityContext.getUserPrincipal());
    commentsThreadService.deleteComment(author, commentId);
    return Response
            .status(Response.Status.NO_CONTENT)
            .build();
  }

  @PATCH
  @Path("/comments/{commentId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Edit comment. Available to author of the comment and admin")
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = CommentPostDto.class)))
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class)))
  public Response updateComment(
          CommentPostDto commentPostDto,
          @PathParam("commentId") Integer commentId,
          @Context SecurityContext securityContext
  ) {
    User author = userService.findByPrincipal(securityContext.getUserPrincipal());
    CommentDto commentDto = commentsThreadService.updateComment(author, commentId, commentPostDto);
    return Response
            .status(Response.Status.OK)
            .entity(commentDto)
            .build();
  }

  @PATCH
  @Path("/{threadId}/resolve")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Resolve thread. Available to author of the thread and admin")
  @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(schema = @Schema(implementation = CommentsThreadDto.class)))
  @ApiResponse(
          responseCode = "400",
          description = "Bad request",
          content = @Content(schema = @Schema(implementation = ExceptionValidationDto.class))
  )
  public Response resolveCommentsThread(
          CommentsThreadResolveDto commentsThreadResolveDto,
          @PathParam("threadId") Integer commentsThreadId,
          @Context SecurityContext securityContext
  ) {
    User author = userService.findByPrincipal(securityContext.getUserPrincipal());
    CommentsThreadDto commentsThreadDto = commentsThreadService.resolveThread(author, commentsThreadId, commentsThreadResolveDto);
    return Response
            .status(Response.Status.OK)
            .entity(commentsThreadDto)
            .build();
  }


}
