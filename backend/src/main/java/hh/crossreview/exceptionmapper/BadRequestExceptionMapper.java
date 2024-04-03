package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorSingleMessageDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ErrorSingleMessageDto(exception))
        .build();
  }

}
