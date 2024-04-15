package hh.crossreview.converter;

import hh.crossreview.entity.Homework;
import hh.crossreview.entity.HomeworkRecord;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.HomeworkRecordStatus;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class HomeworkRecordConverter {

  public HomeworkRecord convertToHomeworkRecord(Homework homework, User student) {
    return new HomeworkRecord()
        .setStatus(HomeworkRecordStatus.ATTEMPTED)
        .setApproveScore(0)
        .setReviewScore(0)
        .setHomework(homework)
        .setStudent(student);
  }

}
