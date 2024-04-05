package hh.crossreview.configuration;

import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.service.UserService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final UserService userService;
  private final JwtRequestFilter jwtRequestFilter;
  private final PasswordEncoder passwordEncoder;

  public SecurityConfig(UserService userService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtRequestFilter = jwtRequestFilter;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(request -> {
              var corsConfiguration = new CorsConfiguration();
              corsConfiguration.setAllowedOriginPatterns(List.of("*"));
              corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
              corsConfiguration.setAllowedHeaders(List.of("*"));
              corsConfiguration.setAllowCredentials(true);
              return corsConfiguration;
            }))
            .authorizeRequests()
            .requestMatchers(HttpMethod.POST, "/api/homeworks").hasAnyAuthority(UserRole.ADMIN.toString(), UserRole.TEACHER.toString())
            .requestMatchers(HttpMethod.PATCH, "/api/homeworks").hasAnyAuthority(UserRole.ADMIN.toString(), UserRole.TEACHER.toString())
            .requestMatchers(HttpMethod.DELETE, "/api/homeworks").hasAnyAuthority(UserRole.ADMIN.toString(), UserRole.TEACHER.toString())
            .requestMatchers("/api/homeworks/**").authenticated()
            .requestMatchers("/api/hello/**").hasAuthority(UserRole.ADMIN.toString())
            .anyRequest().permitAll()
            .and()
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userService);
    return daoAuthenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
