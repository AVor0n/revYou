package hh.crossreview.service;


import hh.crossreview.converter.HomeworkConverter;
import hh.crossreview.converter.HomeworkRecordConverter;
import hh.crossreview.converter.SolutionConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.HomeworkRecordDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionPostResponseDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.HomeworkRecord;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.HomeworkRecordStatus;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.external.gitlab.GitlabService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

@Named
@Singleton
public class SolutionService extends GenericService {

  private final GitlabService gitlabService;
  private final HomeworkDao homeworkDao;
  private final HomeworkRecordDao homeworkRecordDao;
  private final SolutionDao solutionDao;
  private final HomeworkRecordConverter homeworkRecordConverter;
  private final SolutionConverter solutionConverter;
  private final HomeworkConverter homeworkConverter;

  public SolutionService(
      UserDao userDao,
      GitlabService gitlabService,
      HomeworkDao homeworkDao,
      HomeworkRecordDao homeworkRecordDao,
      SolutionDao solutionDao, HomeworkRecordConverter homeworkRecordConverter,
      SolutionConverter solutionConverter,
      HomeworkConverter homeworkConverter
  ) {
    super(userDao);
    this.gitlabService = gitlabService;
    this.homeworkDao = homeworkDao;
    this.homeworkRecordDao = homeworkRecordDao;
    this.solutionDao = solutionDao;
    this.homeworkRecordConverter = homeworkRecordConverter;
    this.solutionConverter = solutionConverter;
    this.homeworkConverter = homeworkConverter;
  }

  @Transactional
  public SolutionPostResponseDto createSolution(
      SolutionPostDto solutionPostDto,
      Integer homeworkId,
      UsernamePasswordAuthenticationToken token
  ) {
    User user = retrieveUserFromToken(token);
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));
    requireUserHasRole(user, UserRole.STUDENT);
    requireValidCohorts(user.getCohorts(), homework);
    solutionPostDto.setBranchLink(trimBranchLink(solutionPostDto.getBranchLink()));
    if (!solutionDao.findByBranchLinkForOtherUser(solutionPostDto.getBranchLink(), user).isEmpty()) {
      throw new BadRequestException("Branch link already send by another user");
    }

    int attemptNumber = getAndValidateAttemptNumber(homework, user);
    String commitId = gitlabService.retrieveCommitId(solutionPostDto.getBranchLink());
    solutionPostDto.setAttemptNumber(attemptNumber);
    solutionPostDto.setCommitId(commitId);

    Solution solution = solutionConverter.convertToSolution(solutionPostDto, homework, user);
    solutionDao.save(solution);
    return new SolutionPostResponseDto(
        solution.getSolutionId(),
        solution.getAttemptNumber()
    );
  }

  @Transactional
  public SolutionsWrapperDto getSolutions(Integer homeworkId) {
    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    HomeworkDto homeworkDto = homeworkConverter.convertToHomeworkDto(homework);
    return new SolutionsWrapperDto(homeworkRecordDao
        .findStudentsByHomeworkAndStatus(homework, HomeworkRecordStatus.ATTEMPTED)
        .stream()
        .map(student -> solutionDao.findLastSolutionByHomeworkAndUser(homework, student))
        .filter(Objects::nonNull)
        .map(solution -> solutionConverter.convertToSolutionDto(solution, homeworkDto))
        .toList());
  }

  private int getAndValidateAttemptNumber(Homework homework, User user) {
    List<HomeworkRecord> homeworkRecords = homeworkRecordDao.findByHomeworkAndStudent(homework, user);
    if (homeworkRecords.isEmpty()) {
      homeworkRecordDao.save(homeworkRecordConverter.convertToHomeworkRecord(homework, user));
      return 1;
    }
    else if (isHomeworkPassedOrSkipped(homeworkRecords)) {
      throw new ForbiddenException("Action is forbidden"); //
    }
    return solutionDao.findLastSolutionByHomeworkAndUser(homework, user).getAttemptNumber() + 1;
  }

  private boolean isHomeworkPassedOrSkipped(List<HomeworkRecord> homeworkRecords) {
    HomeworkRecordStatus status = homeworkRecords.getFirst().getStatus();
    return status.equals(HomeworkRecordStatus.PASSED) ||
        status.equals(HomeworkRecordStatus.SKIPPED);
  }

  private String trimBranchLink(String branchLink) {
    String branchLinkRegex = "(.+/[^/]+/[^/]+/-/tree/[\\w\\-./]+).*";
    Pattern pattern = Pattern.compile(branchLinkRegex);
    Matcher matcher = pattern.matcher(branchLink);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new BadRequestException("Branch link isn't valid");
  }

}
