package hh.crossreview.service;

import hh.crossreview.converter.UserConverter;
import hh.crossreview.dao.CohortDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.user.auth.SignUpRequestDto;
import hh.crossreview.dto.user.info.UserInfoDto;
import hh.crossreview.dto.user.update.PasswordPatchDto;
import hh.crossreview.dto.user.update.UserPatchDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.entity.enums.UserStatus;
import hh.crossreview.utils.RandomUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
  private final CohortDao cohortDao;
  private final PasswordEncoder passwordEncoder;
  private final UserConverter userConverter;

  public UserService(UserDao userDao, CohortDao cohortDao, PasswordEncoder passwordEncoder, UserConverter userConverter) {
    this.userDao = userDao;
    this.cohortDao = cohortDao;
    this.passwordEncoder = passwordEncoder;
    this.userConverter = userConverter;
  }


  public List<User> findByUsername(String username){
    return userDao.findByUsername(username);
  }

  public User findByPrincipal(Principal principal){
    List<User> users = userDao.findByUsername(principal.getName());
    if (users.isEmpty()) {
      throw new ForbiddenException("Bad token");
    }
    return users.getFirst();
  }

  public User findByUserId(Integer userId) {
    var user =  userDao.find(User.class, userId);
    if (user == null) {
      throw new BadRequestException(String.format("User with id %d was not found", userId));
    }
    return user;
  }

  public List<User> findByEmail(String email) {
    return userDao.findByEmail(email);
  }

  public User findById(Integer userId) {
    return userDao.find(User.class, userId);
  }



  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    }
    return generateUserDetails(users.getFirst());
  }

  public org.springframework.security.core.userdetails.User generateUserDetails(User user) {
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

  @Transactional
  public User createNewUser(SignUpRequestDto signUpRequestDto) {
    User user = new User();
    user.setUsername(signUpRequestDto.getUsername());
    user.setEmail(signUpRequestDto.getEmail());
    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
    user.setRole(UserRole.STUDENT);
    user.setStatus(UserStatus.ACTIVE);
    Cohort cohort = RandomUtils.getRandomElement(cohortDao.findAll());
    user.setCohort(cohort);
    userDao.createUser(user);
    return user;

  }

  @Transactional
  public void updatePassword(Principal principal, PasswordPatchDto passwordPatchDto) {
    String password = passwordPatchDto.getPassword();
    String confirmationPassword = passwordPatchDto.getConfirmationPassword();
    if (!Objects.equals(password, confirmationPassword)) {
      throw new BadRequestException("Password mismatch");
    }
    findByPrincipal(principal).setPassword(passwordEncoder.encode(password));
  }

  @Transactional
  public UserInfoDto updateProfile(Principal principal, UserPatchDto userPatchDto) {
    User user = findByPrincipal(principal);
    userConverter.merge(user, userPatchDto);
    return new UserInfoDto(user);
  }

}
