package hh.crossreview.service;


import hh.crossreview.converter.SolutionConverter;
import hh.crossreview.dao.SolutionAttemptDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPatchDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.external.gitlab.service.GitlabService;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Named
@Singleton
public class SolutionService {
  private final GitlabService gitlabService;
  private final SolutionAttemptDao solutionAttemptDao;
  private final SolutionDao solutionDao;
  private final SolutionConverter solutionConverter;
  private final RequirementsUtils reqUtils;

  public SolutionService(
      GitlabService gitlabService,
      SolutionAttemptDao solutionAttemptDao,
      SolutionDao solutionDao,
      SolutionConverter solutionConverter,
      RequirementsUtils reqUtils
  ) {
    this.gitlabService = gitlabService;
    this.solutionAttemptDao = solutionAttemptDao;
    this.solutionDao = solutionDao;
    this.solutionConverter = solutionConverter;
    this.reqUtils = reqUtils;
  }

  @Transactional
  public SolutionDto createSolution(
      SolutionPostDto solutionPostDto,
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    reqUtils.requireValidCohorts(user.getCohorts(), homework);
    requireSolutionNotExist(homework, user);

    var parsedGitlabLink = gitlabService.validateSolutionBranchLink(solutionPostDto.getBranchLink());

    Solution solution = solutionConverter.convertToSolution(
        parsedGitlabLink,
        homework,
        user
    );
    solutionDao.save(solution);
    return solutionConverter.convertToSolutionDto(solution);
  }

  private void requireSolutionNotExist(Homework homework, User user) {
    Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
    if (solution.isPresent()) {
      throw new BadRequestException("User has already created solution");
    }
  }

  @Transactional
  public void createSolutionAttempt(Solution solution) {
    String commitId = gitlabService.retrieveCommitId(solution.getProjectId(), solution.getBranch());
    SolutionAttempt solutionAttempt = new SolutionAttempt(commitId, solution);
    solutionAttemptDao.save(solutionAttempt);
  }

  public Solution requireSolutionExist(Homework homework, User user) {
    Optional<Solution> solution = solutionDao.findByHomeworkAndStudent(homework, user);
    if (solution.isEmpty()) {
      throw new BadRequestException("User has not created a solution yet");
    }
    return solution.get();
  }

  @Transactional
  public SolutionDto getSolution(
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    Solution solution = requireSolutionExist(homework, user);
    return solutionConverter.convertToSolutionDto(solution);
  }

  @Transactional
  public SolutionsWrapperDto getSolutions(
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRoles(user, List.of(UserRole.TEACHER, UserRole.ADMIN));

    List<Solution> solutions = solutionDao.findByHomework(homework);
    return solutionConverter.convertToSolutionsWrapperDto(solutions);
  }

  @Transactional
  public SolutionDto updateSolution(
      SolutionPatchDto solutionPatchDto,
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    requireReviewAttemptNotExist(homework, user);
    Solution solution = requireSolutionExist(homework, user);

    var parsedGitlabLink = gitlabService.validateSolutionBranchLink(solutionPatchDto.getBranchLink());

    solutionConverter.merge(solution, parsedGitlabLink);
    return solutionConverter.convertToSolutionDto(solution);
  }

  private void requireReviewAttemptNotExist(Homework homework, User user) {
    List<SolutionAttempt> solutionAttempts = solutionAttemptDao.findByHomeworkAndStudent(homework, user);
    if (!solutionAttempts.isEmpty()) {
      throw new BadRequestException("You can't change a branch after a review request");
    }
  }

  @Transactional
  public void deleteSolution(
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);
    reqUtils.requireValidCohorts(user.getCohorts(), homework);
    solutionDao
        .findByHomeworkAndStudent(homework, user)
        .ifPresent(solutionDao::deleteSolution);
  }

}
