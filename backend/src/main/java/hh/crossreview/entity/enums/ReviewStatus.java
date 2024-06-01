package hh.crossreview.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum ReviewStatus {

  REVIEWER_SEARCH, REVIEWER_FOUND, REVIEW_STARTED, CORRECTIONS_REQUIRED, CORRECTIONS_LOADED, APPROVED, ARCHIVED;

  public static List<ReviewStatus> getInProgressStatuses(){
    return Arrays.asList(
            REVIEWER_SEARCH,
            REVIEWER_FOUND,
            REVIEW_STARTED,
            CORRECTIONS_REQUIRED,
            CORRECTIONS_LOADED
    );
  }
}
