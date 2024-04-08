package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
        exception.getMessage()
    );

    return Response
        .serverError()
        .entity(exceptionDto)
        .build();
  }
}
