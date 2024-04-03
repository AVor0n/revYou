package hh.crossreview.service;

import jakarta.ws.rs.NotFoundException;

public class GenericService {

  public void requireNotNull(Object entity, String errorMessage) {
    if (entity == null) {
      throw new NotFoundException(errorMessage);
    }
  }

}
