package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorSingleMessageDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    return Response.serverError()
        .entity(new ErrorSingleMessageDto(exception))
        .build();
  }

}
