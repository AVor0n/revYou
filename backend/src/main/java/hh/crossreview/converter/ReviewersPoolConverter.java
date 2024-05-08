package hh.crossreview.converter;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.Reviewer;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.ReviewerStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.time.LocalDateTime;

@Named
@Singleton
public class ReviewersPoolConverter {

  public Reviewer convertToPoolsReviewer(User user, Homework homework) {
    return new Reviewer()
        .setUser(user)
        .setHomework(homework)
        .setStatus(ReviewerStatus.AVAILABLE)
        .setAppointedAt(LocalDateTime.now());
  }

}
