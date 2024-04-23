package hh.crossreview.service;


import hh.crossreview.converter.SolutionConverter;
import hh.crossreview.dao.SolutionAttemptDao;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.solution.SolutionAttemptDto;
import hh.crossreview.dto.solution.SolutionDto;
import hh.crossreview.dto.solution.SolutionPatchDto;
import hh.crossreview.dto.solution.SolutionPostDto;
import hh.crossreview.dto.solution.SolutionsWrapperDto;
import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Solution;
import hh.crossreview.entity.SolutionAttempt;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.external.gitlab.GitlabService;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    String branchLink = trimBranchLink(solutionPostDto.getBranchLink());
    requireBranchLinkUnique(branchLink);
    requireBranchLinkExist(branchLink);
    solutionPostDto.setBranchLink(trimBranchLink(solutionPostDto.getBranchLink()));

    Solution solution = solutionConverter.convertToSolution(solutionPostDto, homework, user);
    solutionDao.save(solution);
    return solutionConverter.convertToSolutionDto(solution);
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
      Homework homework,
      User user
  ) {
    reqUtils.requireUserHasRole(user, UserRole.STUDENT);

    reqUtils.requireValidCohorts(user.getCohorts(), homework);

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

    String branchLink = trimBranchLink(solutionPatchDto.getBranchLink());
    requireBranchLinkUnique(branchLink);
    requireBranchLinkExist(branchLink);
    solutionPatchDto.setBranchLink(trimBranchLink(solutionPatchDto.getBranchLink()));

    solutionConverter.merge(solution, solutionPatchDto);
    return solutionConverter.convertToSolutionDto(solution);
  }

  private void requireReviewAttemptNotExist(Homework homework, User user) {
    List<SolutionAttempt> solutionAttempts = solutionAttemptDao.findByHomeworkAndStudent(homework, user);
    if (!solutionAttempts.isEmpty()) {
      throw new BadRequestException("You can't change a branch after a review request");
    }
  }

}
