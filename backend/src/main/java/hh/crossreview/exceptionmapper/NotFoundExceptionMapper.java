package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        Response.Status.NOT_FOUND.getStatusCode(),
        exception.getMessage()
    );

    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(exceptionDto)
        .build();
  }
}
