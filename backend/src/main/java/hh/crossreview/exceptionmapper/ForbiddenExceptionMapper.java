package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorMessageDto;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

  @Override
  public Response toResponse(ForbiddenException exception) {
    return Response.status(Response.Status.FORBIDDEN)
        .entity(new ErrorMessageDto(exception))
        .build();
  }

}
