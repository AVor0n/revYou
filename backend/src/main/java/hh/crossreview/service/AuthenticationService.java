package hh.crossreview.service;

import hh.crossreview.dto.user.RefreshAccessTokenRequestDto;
import hh.crossreview.dto.user.SignInRequestDto;
import hh.crossreview.dto.user.SignInResponseDto;
import hh.crossreview.dto.user.SignUpRequestDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.User;
import hh.crossreview.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;


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
      throw new NotAuthorizedException("Incorrect login or password");
    }
    User user = requireUserExist(signInRequestDto.getUsername());
    UserDetails userDetails = userService.generateUserDetails(user);
    String userRole = userDetails.getAuthorities().toString();
    String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
    String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);

    return Response.ok(new SignInResponseDto(accessToken, refreshToken, userRole, user))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }

  @Transactional
  public Response createNewUser(SignUpRequestDto signUpRequestDto) {
    if (!validateSignUpRequest(signUpRequestDto)){
      throw new BadRequestException("Invalid data");
    }
    User user = userService.createNewUser(signUpRequestDto);
    return Response.ok(new UserDto(user.getUserId(), user.getUsername(), user.getEmail()))
            .type(MediaType.APPLICATION_JSON)
            .build();
  }

  public Response refreshToken(RefreshAccessTokenRequestDto refreshAccessTokenRequestDto) {
    String refreshToken = refreshAccessTokenRequestDto.getRefreshToken();
    if (jwtTokenUtils.validateRefreshToken(refreshToken)){
      final Claims claims = jwtTokenUtils.getRefreshClaims(refreshToken);
      final String username = claims.getSubject();
      User user = requireUserExist(username);
      UserDetails userDetails = userService.generateUserDetails(user);
      String userRole = userDetails.getAuthorities().toString();
      final String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
      return Response.ok(new SignInResponseDto(accessToken, refreshToken, userRole, user))
              .type(MediaType.APPLICATION_JSON)
              .build();
    }
    throw new NotAuthorizedException("Invalid JWT token");
  }

  private User requireUserExist(String username) {
    List<User> users = userService.findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    }
    return users.getFirst();
  }

  private boolean validateSignUpRequest(SignUpRequestDto signUpRequestDto) {
    if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getConfirmPassword())) {
      throw new BadRequestException("Password mismatch");
    }
    List<User> usersWithSameUsername = userService.findByUsername(signUpRequestDto.getUsername());
    if (!usersWithSameUsername.isEmpty()) {
      throw new BadRequestException("A user with the specified username already exists");
    }
    List<User> usersWithSameEmail = userService.findByEmail(signUpRequestDto.getEmail());
    if (!usersWithSameEmail.isEmpty()) {
      throw new BadRequestException("A user with the specified email already exists");
    }
    return true;
  }
}
