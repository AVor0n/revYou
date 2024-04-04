package hh.crossreview.service;

import hh.crossreview.dto.user.SignInRequestDto;
import hh.crossreview.dto.user.SignInResponseDto;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.User;
import hh.crossreview.utils.JwtTokenUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


@Named
@Singleton
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

  public Response createAuthToken(SignInRequestDto signInRequestDto) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new NotAuthorizedException("Неправильный логин или пароль");
    }
    UserDetails userDetails = userService.loadUserByUsername(signInRequestDto.getUsername());
    String token = jwtTokenUtils.generateToken(userDetails);
    return Response.ok(new SignInResponseDto(token))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }

  @Transactional
  public Response createNewUser(SignUpRequestDto signUpRequestDto) {
    if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
      throw new BadRequestException("Пароли не совпадают");
    }
    List<User> users = userService.findByUsername(signUpRequestDto.getUsername());
    if (!users.isEmpty()) {
      throw new BadRequestException("Пользователь с указанным именем уже существует");
    }
    User user = userService.createNewUser(signUpRequestDto);
    return Response.ok(new UserDto(user.getUserId(), user.getUsername(), user.getEmail()))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }
}
