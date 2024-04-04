package hh.crossreview.service;

import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.entity.enums.UserStatus;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

  private UserDao userDao;
  private PasswordEncoder passwordEncoder;


  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> findByUsername(String username) throws UsernameNotFoundException {
    return userDao.findByUsername(username);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    } else {
      User user = users.getFirst();
      Collection<String> userRoles = Arrays.asList(user.getRole().name());
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