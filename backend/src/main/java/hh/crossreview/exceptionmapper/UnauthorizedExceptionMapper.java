package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  @Override
  public Response toResponse(NotAuthorizedException exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        Response.Status.UNAUTHORIZED.getStatusCode(),
        exception.getMessage()
    );

    return Response
        .status(Response.Status.UNAUTHORIZED)
        .entity(exceptionDto)
        .build();
  }

}
