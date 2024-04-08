package hh.crossreview.service;

import hh.crossreview.dao.UserDao;
import hh.crossreview.entity.User;
import hh.crossreview.entity.interfaces.Authorable;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class GenericService {

  private final UserDao userDao;

  public GenericService(UserDao userDao) {
    this.userDao = userDao;
  }

  public void requireEntityNotNull(Object entity, String errorMessage) {
    if (entity == null) {
      throw new NotFoundException(errorMessage);
    }
  }

  public void requireAuthorPermission(UsernamePasswordAuthenticationToken token, Authorable authorable) {
    Integer actualId = retrieveUserFromToken(token).getUserId();
    Integer expectedId = authorable.getAuthorId();
    if (!expectedId.equals(actualId)) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }
  }

  public User retrieveUserFromToken(UsernamePasswordAuthenticationToken token) {
    String username = token.getPrincipal().toString();
    List<User> users = userDao.findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("User '%s' not found", username));
    }
    return users.getFirst();
  }

}
