package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorMessageDto;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(new ErrorMessageDto(exception))
        .build();
  }

}
