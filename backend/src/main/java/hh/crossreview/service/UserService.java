package hh.crossreview.service;

import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.entity.enums.UserStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Named
@Singleton
public class UserService implements UserDetailsService {

  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }


  public List<User> findByUsername(String username){
    return userDao.findByUsername(username);
  }

  public List<User> findByEmail(String email) {
    return userDao.findByEmail(email);
  }



  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    } else {
      User user = users.getFirst();
      Collection<String> userRoles = List.of(user.getRole().name());
      List<GrantedAuthority> grantedAuthoritiesRoles = userRoles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              grantedAuthoritiesRoles
      );
    }

  }

  @Transactional
  public User createNewUser(SignUpRequestDto signUpRequestDto) {

    User user = new User();
    user.setUsername(signUpRequestDto.getUsername());
    user.setEmail(signUpRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
    user.setRole(UserRole.STUDENT);
    user.setStatus(UserStatus.ACTIVE);
    userDao.createUser(user);
    return user;

  }
}
