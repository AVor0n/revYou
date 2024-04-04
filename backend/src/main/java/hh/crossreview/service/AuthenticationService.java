package hh.crossreview.service;

import hh.crossreview.dto.exception.AuthenticationException;
import hh.crossreview.dto.user.SignInRequestDto;
import hh.crossreview.dto.user.SignInResponseDto;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.User;
import hh.crossreview.utils.JwtTokenUtils;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class AuthenticationService {
  private final UserService userService;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
          UserService userService,
          JwtTokenUtils jwtTokenUtils,
          AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.jwtTokenUtils = jwtTokenUtils;
    this.authenticationManager = authenticationManager;
  }

  public ResponseEntity<?> createAuthToken(@RequestBody SignInRequestDto signInRequestDto) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword())
      );
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>(
              new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED
      );
    }
    UserDetails userDetails = userService.loadUserByUsername(signInRequestDto.getUsername());
    String token = jwtTokenUtils.generateToken(userDetails);
    return ResponseEntity.ok(new SignInResponseDto(token));
  }

  public ResponseEntity<?> createNewUser(@RequestBody SignUpRequestDto signUpRequestDto) {
    if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
      return new ResponseEntity<>(new AuthenticationException(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
    }
    List<User> users = userService.findByUsername(signUpRequestDto.getUsername());
    if (!users.isEmpty()) {
      return new ResponseEntity<>(
          new AuthenticationException(HttpStatus.BAD_REQUEST.value(),
          "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST
      );
    }
    User user = userService.createNewUser(signUpRequestDto);
    return ResponseEntity.ok(new UserDto(user.getUserId(), user.getUsername(), user.getEmail()));
  }
}
