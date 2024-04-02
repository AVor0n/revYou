package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorMessageDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    return Response.serverError()
        .entity(new ErrorMessageDto(exception))
        .build();
  }

}
