package hh.crossreview.integration.resource;

import hh.crossreview.dto.homework.HomeworksWrapperDto;
import hh.crossreview.integration.BaseIT;
import jakarta.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

class HomeworkResourceIT extends BaseIT {

  @Inject
  private TestRestTemplate testRestTemplate;

  private final String homeworksPath = "/homeworks";

  @Test
  void givenHomeworks_whenGetHomeworksCall_thenReturnsHomeworks() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(getAdminToken());

    String url = getBasePath() + homeworksPath;
    ResponseEntity<HomeworksWrapperDto> response = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), HomeworksWrapperDto.class);
    HomeworksWrapperDto homeworksWrapperDto = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(homeworksWrapperDto);
    assertNotNull(homeworksWrapperDto.getData());
    assertEquals(3, homeworksWrapperDto.getData().size());
  }

}
