package hh.crossreview.utils;

import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.entity.interfaces.Authorable;
import hh.crossreview.entity.interfaces.Cohortable;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
@Singleton
public class RequirementsUtils {

  public void requireEntityNotNull(Object entity, String errorMessage) {
    if (entity == null) {
      throw new NotFoundException(errorMessage);
    }
  }

  public void requireAuthorPermissionOrAdmin(User user, Authorable authorable) {
    if (!user.getRole().equals(UserRole.ADMIN)) {
      requireAuthorPermission(user, authorable);
    }
  }

  public void requireAuthorPermission(User user, Authorable authorable) {
    Integer actualId = user.getUserId();
    Integer expectedId = authorable.getAuthorId();
    if (!expectedId.equals(actualId)) {
      throw new ForbiddenException("User doesn't have permissions for this action");
    }
  }

  public void requireUserHasRole(User user, UserRole userRole) {
    if (!user.getRole().equals(userRole)) {
      throw new ForbiddenException(String.format("Action available only for %s", userRole.toString()));
    }
  }

  public void requireValidCohorts(List<Cohort> cohorts, Cohortable cohortable) {
    Map<Integer, Cohort> cohortsById = cohorts
        .stream()
        .collect(Collectors.toMap(Cohort::getCohortId, Function.identity()));
    cohortable
        .getCohorts()
        .stream()
        .filter(cohort -> cohortsById.containsKey(cohort.getCohortId()))
        .findAny()
        .orElseThrow(() -> new ForbiddenException("You study direction isn't valid"));
  }

  public void requireUserHasRoles(User user, List<UserRole> userRoles) {
    if (!userRoles.contains(user.getRole())) {
      throw new ForbiddenException(String.format("Action not available for %s", user.getRole().toString()));
    }
  }
}
