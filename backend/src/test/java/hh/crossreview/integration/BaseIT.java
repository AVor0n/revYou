package hh.crossreview.integration;

import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.Review;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewDuration;
import hh.crossreview.entity.enums.ReviewStatus;
import hh.crossreview.entity.enums.ReviewerStatus;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.StudyDirection;
import hh.crossreview.entity.enums.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@SuppressWarnings("resource")
public abstract class BaseIT {

  static final PostgreSQLContainer<?> postgreSQLContainer;

  static {
    postgreSQLContainer =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("cross_review")
            .withUsername("cross")
            .withPassword("review")
            .withReuse(true);

    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void datasourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }

  @PersistenceContext
  private EntityManager entityManager;

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  protected User createUser(
      UserRole userRole,
      Cohort cohort,
      String username,
      String email) {
    User user = new User();
    user.setRole(userRole);
    user.setCohort(cohort);
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword("pass");
    return user;
  }

  protected Homework createBackendHomework() {
    Cohort backCohort = createCohort(StudyDirection.BACK);
    User backendTeacher = createUser(UserRole.TEACHER, backCohort, "user1", "email1");
    Lecture backendLecture = createLecture(backendTeacher, List.of(backCohort));
    return createHomework(backendTeacher, backendLecture);
  }

  protected Homework createFrontendHomework() {
    Cohort frontCohort = createCohort(StudyDirection.FRONT);
    User frontendTeacher = createUser(UserRole.TEACHER, frontCohort, "user2", "email2");
    Lecture frontendLecture = createLecture(frontendTeacher, List.of(frontCohort));
    return createHomework(frontendTeacher, frontendLecture);
  }

  protected Review createReview(
      User student,
      User reviewer,
      ReviewStatus status,
      Solution solution
  ) {
    Review review = new Review();
    review.setStudent(student);
    review.setReviewer(reviewer);
    review.setStatus(status);
    review.setSolution(solution);
    return review;
  }

  protected Solution createSolution(
      SolutionStatus solutionStatus,
      Integer approveScore,
      Integer reviewScore,
      Homework homework,
      User student,
      Integer projectId,
      String branch,
      String sourceCommitId
  ) {
    return new Solution()
        .setStatus(solutionStatus)
        .setApproveScore(approveScore)
        .setReviewScore(reviewScore)
        .setHomework(homework)
        .setStudent(student)
        .setProjectId(projectId)
        .setBranch(branch)
        .setSourceCommitId(sourceCommitId);
  }

  protected Reviewer createReviewer(
      ReviewerStatus reviewerStatus,
      LocalDateTime availableAt,
      User user,
      Homework homework
  ) {
    return new Reviewer()
        .setStatus(reviewerStatus)
        .setAppointedAt(availableAt)
        .setUser(user)
        .setHomework(homework);
  }

  protected Homework createHomework(
      User author,
      Lecture lecture) {
    return new Homework()
        .setAuthor(author)
        .setLecture(lecture)
        .setName("Name")
        .setCompletionDeadline(Date.from(Instant.parse("2024-04-25T00:00:00Z")))
        .setStartDate(Date.from(Instant.parse("2024-04-30T00:00:00Z")))
        .setTopic("Topic")
        .setReviewDuration(ReviewDuration.TWO_DAYS)
        .setDescription("Description")
        .setRepositoryLink("repositoryLink");
  }

  protected Lecture createLecture(User lector, List<Cohort> cohorts) {
    return new Lecture()
        .setLector(lector)
        .setCohorts(cohorts);
  }

  protected Cohort createCohort(StudyDirection studyDirection) {
    return new Cohort().setStudyDirection(studyDirection);
  }


}
