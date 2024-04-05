package hh.crossreview.service;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

public class GenericService {

  public void requireEntityNotNull(Object entity, String errorMessage) {
    if (entity == null) {
      throw new NotFoundException(errorMessage);
    }
  }

  public void requireUserIdEquals(Integer userIdExpected, Integer userIdActual) {
    if (!userIdExpected.equals(userIdActual)) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }
  }

}
