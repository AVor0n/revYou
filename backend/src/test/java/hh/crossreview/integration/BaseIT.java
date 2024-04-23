package hh.crossreview.integration;

import hh.crossreview.dto.user.SignInRequestDto;
import hh.crossreview.dto.user.SignInResponseDto;
import jakarta.inject.Inject;
import java.util.Objects;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseIT {


  static final PostgreSQLContainer<?> postgreSQLContainer;

  static {
    postgreSQLContainer =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("cross_review")
            .withUsername("cross")
            .withPassword("review")
            .withInitScript("init-tests.sql")
            .withReuse(true);

    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void datasourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }

  @LocalServerPort
  private int randomServerPort;

  @Inject
  protected TestRestTemplate testRestTemplate;

  protected String getBasePath() {
    return String.format("http://localhost:%s/api", randomServerPort);
  }

  protected String getTeacherToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    SignInRequestDto signInRequestDto = new SignInRequestDto("username_3", "100");
    HttpEntity<SignInRequestDto> request = new HttpEntity<>(signInRequestDto, headers);

    String url = String.format("%s/auth/sign-in", getBasePath());
    return Objects
        .requireNonNull(testRestTemplate.postForEntity(url, request, SignInResponseDto.class).getBody())
        .getAccessToken();
  }

  protected String getUserToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    SignInRequestDto signInRequestDto = new SignInRequestDto("username_1", "100");
    HttpEntity<SignInRequestDto> request = new HttpEntity<>(signInRequestDto, headers);

    String url = String.format("%s/auth/sign-in", getBasePath());
    return Objects
        .requireNonNull(testRestTemplate.postForEntity(url, request, SignInResponseDto.class).getBody())
        .getAccessToken();
  }

  protected String getAdminToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    SignInRequestDto signInRequestDto = new SignInRequestDto("username_6", "100");
    HttpEntity<SignInRequestDto> request = new HttpEntity<>(signInRequestDto, headers);

    String url = String.format("%s/auth/sign-in", getBasePath());
    return Objects
        .requireNonNull(testRestTemplate.postForEntity(url, request, SignInResponseDto.class).getBody())
        .getAccessToken();
  }

}
