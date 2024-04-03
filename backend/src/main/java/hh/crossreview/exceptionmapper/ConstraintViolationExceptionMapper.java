package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ErrorSeveralMessagesDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(createEntity(exception))
        .build();
  }

  private ErrorSeveralMessagesDto createEntity(ConstraintViolationException exception) {
    List<String> messages = exception.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .toList();
    return new ErrorSeveralMessagesDto(messages);
  }

}
