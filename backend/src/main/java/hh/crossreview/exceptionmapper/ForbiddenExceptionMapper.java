package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

  @Override
  public Response toResponse(ForbiddenException exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        Response.Status.FORBIDDEN.getStatusCode(),
        exception.getMessage()
    );

    return Response
        .status(Response.Status.FORBIDDEN)
        .entity(exceptionDto)
        .build();
  }
}
