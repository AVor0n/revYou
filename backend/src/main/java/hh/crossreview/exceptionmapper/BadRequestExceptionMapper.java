package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        Response.Status.BAD_REQUEST.getStatusCode(),
        exception.getMessage()
    );

    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(exceptionDto)
        .build();
  }

}
