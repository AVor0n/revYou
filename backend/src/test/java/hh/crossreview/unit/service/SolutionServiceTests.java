package hh.crossreview.unit.service;

import hh.crossreview.converter.SolutionConverter;
import hh.crossreview.dao.SolutionAttemptDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPatchDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.SolutionStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.external.gitlab.GitlabService;
import hh.crossreview.service.SolutionService;
import hh.crossreview.unit.TestsUtil;
import hh.crossreview.utils.RequirementsUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolutionServiceTests extends TestsUtil {

  @Mock
  private GitlabService gitlabService;
  @Mock
  private SolutionAttemptDao solutionAttemptDao;
  @Mock
  private SolutionDao solutionDao;
  @Mock
  @SuppressWarnings("unused")
  private RequirementsUtils reqUtils;
  @Spy
  @SuppressWarnings("unused")
  private final SolutionConverter solutionConverter = new SolutionConverter();
  @InjectMocks
  private SolutionService solutionService;

  @Test
  void givenCreateSolutionCall_whenValid_thenSuccessfullyCreate() {
    Homework homework = createBackHomework();
    String branchLink = "gitlab/username/repository/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    SolutionPostDto solutionPostDto = createSolutionPostDto(branchLink);
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));

    when(solutionDao.findByHomeworkAndStudent(homework, student)).thenReturn(Optional.empty());
    when(solutionDao.findByBranchLink(any(String.class))).thenReturn(Optional.empty());
    doNothing().when(gitlabService).checkBranchLink(any(String.class));
    doNothing().when(solutionDao).save(any(Solution.class));
    SolutionDto solutionDto = solutionService.createSolution(solutionPostDto, homework, student);

    assertTrue(solutionDto.getSolutionAttempts().isEmpty());
    assertEquals(solutionPostDto.getBranchLink(), solutionDto.getBranchLink());
    assertEquals(SolutionStatus.IN_PROGRESS.toString(), solutionDto.getStatus());
    assertEquals(student.getUserId(), solutionDto.getStudentId());
    assertEquals(0, solutionDto.getApproveScore());
    assertEquals(0, solutionDto.getReviewScore());
  }

  @Test
  void givenCreateSolutionAttemptCall_whenValid_thenSuccessfullyCreate() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    String branchLink = "gitlab/username/repository/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    String commitId = "aabbccddeeff";
    Solution solution = createSolution(1, SolutionStatus.IN_PROGRESS, student, branchLink, Collections.emptyList());

    when(solutionDao.findByHomeworkAndStudent(homework, student)).thenReturn(Optional.of(solution));
    when(gitlabService.retrieveCommitId(branchLink)).thenReturn(commitId);
    doNothing().when(solutionAttemptDao).save(any(SolutionAttempt.class));
    SolutionAttemptDto solutionAttemptDto = solutionService.createSolutionAttempt(homework, student);

    assertEquals(commitId, solutionAttemptDto.getCommitId());
    assertNotNull(solutionAttemptDto.getCreatedAt());
  }

  @Test
  void givenGetSolutionCall_whenValid_thenSuccessfullyGet() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    String branchLink = "gitlab/username/repository/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    SolutionAttempt solutionAttempt = createSolutionAttempt("commitId", LocalDateTime.parse("2024-04-26T10:15:30"));
    Solution solution = createSolution(1, SolutionStatus.IN_PROGRESS, student, branchLink, List.of(solutionAttempt));

    when(solutionDao.findByHomeworkAndStudent(homework, student)).thenReturn(Optional.of(solution));
    SolutionDto solutionDto = solutionService.getSolution(homework, student);

    assertEquals(student.getUserId(), solutionDto.getStudentId());
    assertEquals(1, solutionDto.getSolutionAttempts().size());
  }

  @Test
  void givenGetSolutionsCall_whenValid_thenSuccessfullyGet() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.TEACHER, homework.getCohorts().get(0));
    String branchLink = "gitlab/username/repository/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    SolutionAttempt solutionAttempt = createSolutionAttempt("commitId", LocalDateTime.parse("2024-04-26T10:15:30"));
    Solution solution = createSolution(1, SolutionStatus.IN_PROGRESS, student, branchLink, List.of(solutionAttempt));

    when(solutionDao.findByHomework(homework)).thenReturn(List.of(solution));
    List<SolutionDto> solutionsDto = solutionService.getSolutions(homework, student).getData();

    assertEquals(1, solutionsDto.size());
    assertEquals(solution.getStatus().toString(), solutionsDto.get(0).getStatus());
    assertEquals(1, solution.getSolutionAttempts().size());
    assertEquals(solutionAttempt.getCommitId(), solutionsDto.get(0).getSolutionAttempts().get(0).getCommitId());
  }

  @Test
  void givenUpdateSolutionCall_whenValid_thenSuccessfullyUpdate() {
    Homework homework = createBackHomework();
    User student = createUser(1, UserRole.STUDENT, homework.getCohorts().get(0));
    String oldBranchLink = "gitlab/username/repository/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    Solution oldSolution = createSolution(1, SolutionStatus.IN_PROGRESS, student, oldBranchLink, Collections.emptyList());
    String newBranchLink = "gitlab/usernameNew/repositoryNew/-/tree/branch-name.with-dot/and-slash?abc=1&some=2";
    SolutionPatchDto solutionPatchDto = createSolutionPatchDto(newBranchLink);

    when(solutionDao.findByHomeworkAndStudent(homework, student)).thenReturn(Optional.of(oldSolution));
    when(solutionDao.findByBranchLink(any(String.class))).thenReturn(Optional.empty());
    when(solutionAttemptDao.findByHomeworkAndStudent(homework, student)).thenReturn(Collections.emptyList());
    doNothing().when(gitlabService).checkBranchLink(any(String.class));
    SolutionDto solutionDto = solutionService.updateSolution(solutionPatchDto, homework, student);

    String trimmedBranch = "gitlab/usernameNew/repositoryNew/-/tree/branch-name.with-dot/and-slash";
    assertEquals(trimmedBranch, solutionDto.getBranchLink());
  }

  SolutionPostDto createSolutionPostDto(String branchLink) {
    SolutionPostDto solutionPostDto = new SolutionPostDto();
    solutionPostDto.setBranchLink(branchLink);
    return solutionPostDto;
  }

  SolutionPatchDto createSolutionPatchDto(String branchLink) {
    SolutionPatchDto solutionPatchDto = new SolutionPatchDto();
    solutionPatchDto.setBranchLink(branchLink);
    return solutionPatchDto;
  }

  @SuppressWarnings("SameParameterValue")
  Solution createSolution(
      Integer solutionId,
      SolutionStatus solutionStatus,
      User student,
      String branchLink,
      List<SolutionAttempt> solutionAttempts
  ) {
    return new Solution()
        .setSolutionId(solutionId)
        .setStatus(solutionStatus)
        .setStudent(student)
        .setBranchLink(branchLink)
        .setSolutionAttempts(solutionAttempts);
  }

  @SuppressWarnings("SameParameterValue")
  SolutionAttempt createSolutionAttempt(
      String commitId,
      LocalDateTime createdAt
  ) {
    return new SolutionAttempt()
        .setCommitId(commitId)
        .setCreatedAt(createdAt);
  }

}
