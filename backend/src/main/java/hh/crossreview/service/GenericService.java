package hh.crossreview.service;

import jakarta.ws.rs.NotFoundException;

public class GenericService {

  public void checkEntityNotNull(Object entity, String message) {
    if (entity == null) {
      throw new NotFoundException(message);
    }
  }

}
