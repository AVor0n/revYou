package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorSingleMessageDto;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  @Override
  public Response toResponse(NotAuthorizedException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorSingleMessageDto(exception))
            .build();
  }

}
