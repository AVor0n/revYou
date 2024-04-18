package hh.crossreview.service;


import hh.crossreview.converter.SolutionConverter;
import hh.crossreview.dao.HomeworkDao;
import hh.crossreview.dao.SolutionAttemptDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionPostResponseDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.external.gitlab.GitlabService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

@Named
@Singleton
public class SolutionService extends GenericService {

  private final GitlabService gitlabService;
  private final HomeworkDao homeworkDao;
  private final SolutionAttemptDao solutionAttemptDao;
  private final SolutionDao solutionDao;
  private final SolutionConverter solutionConverter;

  public SolutionService(
      UserDao userDao,
      GitlabService gitlabService,
      HomeworkDao homeworkDao,
      SolutionAttemptDao solutionAttemptDao,
      SolutionDao solutionDao,
      SolutionConverter solutionConverter
  ) {
    super(userDao);
    this.gitlabService = gitlabService;
    this.homeworkDao = homeworkDao;
    this.solutionAttemptDao = solutionAttemptDao;
    this.solutionDao = solutionDao;
    this.solutionConverter = solutionConverter;
  }

  @Transactional
  public SolutionPostResponseDto createSolution(
      SolutionPostDto solutionPostDto,
      Integer homeworkId,
      UsernamePasswordAuthenticationToken token
  ) {
    User user = retrieveUserFromToken(token);
    requireUserHasRole(user, UserRole.STUDENT);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));

    requireValidCohorts(user.getCohorts(), homework);
    requireSolutionNotExist(homework, user);

    String branchLink = trimBranchLink(solutionPostDto.getBranchLink());
    requireBranchLinkUnique(branchLink);
    requireBranchLinkExist(branchLink);
    solutionPostDto.setBranchLink(trimBranchLink(solutionPostDto.getBranchLink()));

    Solution solution = solutionConverter.convertToSolution(solutionPostDto, homework, user);
    solutionDao.save(solution);
    return new SolutionPostResponseDto(
        solution.getSolutionId()
    );
  }

  private void requireBranchLinkExist(String branchLink) {
    gitlabService.checkBranchLink(branchLink);
  }

  private void requireBranchLinkUnique(String branchLink) {
    if (solutionDao.findByBranchLink(branchLink).isPresent()) {
      throw new BadRequestException("Branch link already send by another user or to another homework");
    }
  }

  private void requireSolutionNotExist(Homework homework, User user) {
    Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
    if (solution.isPresent()) {
      throw new BadRequestException("User has already created solution");
    }
  }


  @Transactional
  public SolutionAttemptDto createSolutionAttempt(
      Integer homeworkId,
      UsernamePasswordAuthenticationToken token
  ) {
    User user = retrieveUserFromToken(token);
    requireUserHasRole(user, UserRole.STUDENT);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));

    requireValidCohorts(user.getCohorts(), homework);

    Solution solution = requireSolutionExist(homework, user);
    String commitId = gitlabService.retrieveCommitId(solution.getBranchLink());
    SolutionAttempt solutionAttempt = new SolutionAttempt(commitId, solution);
    solutionAttemptDao.save(solutionAttempt);
    return new SolutionAttemptDto(
        solutionAttempt.getCommitId(),
        solutionAttempt.getCreatedAt()
    );
  }

  private Solution requireSolutionExist(Homework homework, User user) {
    Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
    if (solution.isEmpty()) {
      throw new BadRequestException("User has not created a solution yet");
    }
    return solution.get();
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

  @Transactional
  public SolutionDto readSolution(
      Integer homeworkId,
      UsernamePasswordAuthenticationToken token
  ) {
    User user = retrieveUserFromToken(token);
    requireUserHasRole(user, UserRole.STUDENT);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));

    Solution solution = requireSolutionExist(homework, user);
    return solutionConverter.convertToSolutionDto(solution);
  }

  @Transactional
  public SolutionsWrapperDto readSolutions(
      Integer homeworkId,
      UsernamePasswordAuthenticationToken token
  ) {
    User user = retrieveUserFromToken(token);
    requireUserHasRole(user, UserRole.TEACHER);

    Homework homework = homeworkDao.find(Homework.class, homeworkId);
    requireEntityNotNull(homework, String.format("Homework with id %d was not found", homeworkId));

    List<Solution> solutions = solutionDao.findByHomework(homework);
    return solutionConverter.convertToSolutionsWrapperDto(solutions);
  }
}
