package hh.crossreview.exceptionmapper;

import hh.crossreview.dto.exception.ExceptionValidationDto;
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
    ExceptionValidationDto exceptionValidationDto = new ExceptionValidationDto(
        Response.Status.BAD_REQUEST.getStatusCode(),
        "Some fields are invalid",
        createMessages(exception)
    );

    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(exceptionValidationDto)
        .build();
  }

  private List<String> createMessages(ConstraintViolationException exception) {
    return exception
        .getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .toList();
  }

}
