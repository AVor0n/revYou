package hh.crossreview.configuration.securitytools;

import com.fasterxml.jackson.databind.ObjectMapper;
import hh.crossreview.dto.exception.ExceptionDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  public AuthenticationExceptionEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AuthenticationException e) throws IOException {
    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
    httpServletResponse.setCharacterEncoding("UTF-8");

    ServletOutputStream out = httpServletResponse.getOutputStream();
    objectMapper.writeValue(out, new ExceptionDto(
        HttpServletResponse.SC_FORBIDDEN,
        "Action is forbidden"));
    out.flush();
  }
}
