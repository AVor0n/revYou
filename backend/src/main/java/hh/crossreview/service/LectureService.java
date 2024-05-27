package hh.crossreview.service;

import hh.crossreview.converter.LectureConverter;
import hh.crossreview.dao.CohortDao;
import hh.crossreview.dao.LectureDao;
import hh.crossreview.dao.UserDao;
import hh.crossreview.dto.lecture.LectureDto;
import hh.crossreview.dto.lecture.LecturePatchDto;
import hh.crossreview.dto.lecture.LecturePostDto;
import hh.crossreview.dto.lecture.LecturePostPesponseDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import hh.crossreview.entity.enums.UserRole;
import hh.crossreview.utils.RequirementsUtils;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
public class LectureService extends GenericService {
  private final LectureDao lectureDao;
  private final LectureConverter lectureConverter;
  private final RequirementsUtils reqUtils;
  private final CohortDao cohortDao;

  public LectureService(UserDao userDao, LectureDao lectureDao, LectureConverter lectureConverter, RequirementsUtils reqUtils, CohortDao cohortDao) {
    super(userDao);
    this.lectureDao = lectureDao;
    this.lectureConverter = lectureConverter;
    this.reqUtils = reqUtils;
    this.cohortDao = cohortDao;
  }

  @Transactional
  public void updateLecture(Integer lectureId, LecturePatchDto lecturePatchDto, User lector) {
    Lecture lecture = lectureDao.find(Lecture.class, lectureId);
    reqUtils.requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", lectureId));
    reqUtils.requireAuthorPermissionOrAdmin(lector, lecture);
    lectureConverter.merge(lecture, lecturePatchDto, lector);
    lectureDao.save(lecture);

  }

  @Transactional
  public LectureDto getLecture(Integer id) {
    Lecture lecture = lectureDao.getLecture(id);
    requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", id));
    return lectureConverter.convertToLectureDto(lecture);
  }

  @Transactional
  public void deleteLecture(Integer id, User lector) {
    Lecture lecture = lectureDao.find(Lecture.class, id);
    reqUtils.requireEntityNotNull(lecture, String.format("Lecture with id %d was not found", id));
    reqUtils.requireAuthorPermissionOrAdmin(lector, lecture);
    lectureDao.deleteLecture(lecture);
  }

  @Transactional
  public LecturePostPesponseDto createLecture(LecturePostDto lecturePostDto, User lector) {
    var cohorts = lecturePostDto.getCohortsId().stream().map(id -> {
      var cohort = cohortDao.find(Cohort.class, id);
      reqUtils.requireEntityNotNull(cohort, String.format("Cohort with id %d was not found", id));
      return cohort;
    }).toList();
    Lecture lecture = lectureConverter.convertToLecture(lecturePostDto, lector, cohorts);
    lectureDao.save(lecture);
    return lectureConverter.convertToLecturePostResponseDto(lecture.getLectureId());
  }

  @Transactional
  public List<LectureDto> getLectures(User user) {
    if (user.getRole() == UserRole.STUDENT) {
      var cohortId = user.getCohort().getCohortId();
      var cohort = cohortDao.find(Cohort.class, cohortId);
      reqUtils.requireEntityNotNull(cohort, String.format("Cohort with id %d was not found", cohortId));
      return cohort.getLectures().stream().map(lectureConverter::convertToLectureDto).toList();
    }
    var lectures = lectureDao.findLecturesByAuthor(user);
    return lectures.stream().map(lectureConverter::convertToLectureDto).toList();
  }

}
