package hh.crossreview.dao;

import hh.crossreview.entity.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;

@Named
@Singleton
public class UserDao extends GenericDao{

  public List<User> findByUsername(String username) {
    return  getEntityManager()
            .createQuery(
          "SELECT u FROM User u " +
                    "WHERE u.username = :username",
                    User.class)
            .setParameter("username", username)
            .getResultList();
  }

  public List<User> findByEmail(String email) {
    return  getEntityManager()
            .createQuery(
                    "SELECT u FROM User u " +
                            "WHERE u.email = :email",
                    User.class)
            .setParameter("email", email)
            .getResultList();
  }

  public void createUser(User user) {
    getEntityManager().persist(user);
  }
}
