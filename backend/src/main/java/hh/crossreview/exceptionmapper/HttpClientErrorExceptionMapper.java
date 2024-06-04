package hh.crossreview.exceptionmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.inject.Named;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.springframework.web.client.HttpClientErrorException;

@Provider
@Named
public class HttpClientErrorExceptionMapper implements ExceptionMapper<HttpClientErrorException> {

  private final ObjectMapper objectMapper;

  public HttpClientErrorExceptionMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Response toResponse(HttpClientErrorException exception) {
    ExceptionDto exceptionDto = new ExceptionDto(
        exception.getStatusCode().value(),
        retrieveMessage(exception)
    );

    return Response
        .status(exception.getStatusCode().value())
        .entity(exceptionDto)
        .build();
  }

  private String retrieveMessage(HttpClientErrorException exception) {
    try {
      return objectMapper
          .readTree(exception.getResponseBodyAsString())
          .get("message")
          .asText();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

}
