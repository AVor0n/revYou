package hh.crossreview.converter;

import hh.crossreview.dto.lecture.LectureDto;
import hh.crossreview.dto.lecture.LecturePatchDto;
import hh.crossreview.dto.lecture.LecturePostDto;
import hh.crossreview.dto.lecture.LecturePostPesponseDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.Cohort;
import hh.crossreview.entity.Lecture;
import hh.crossreview.entity.User;
import jakarta.inject.Named;

import java.util.List;

@Named
public class LectureConverter {
    public LectureDto convertToLectureDto(Lecture lecture) {
        return new LectureDto().setLectureId(lecture.getLectureId()).setName(lecture.getName())
                .setLectureDate(lecture.getLectureDate()).setLectureId(lecture.getLectureId()).
                setZoomLink(lecture.getZoomLink()).setPresentationLink(lecture.getPresentationLink())
                .setLector(convertToUserDto(lecture.getLector()));
    }

    public UserDto convertToUserDto(User lector) {
        return new UserDto().setUserId(lector.getUserId())
                .setEmail(lector.getEmail())
                .setUsername(lector.getUsername());
    }

    public Lecture convertToLecture(LecturePostDto lecturePostDto, User lector, List<Cohort> cohorts) {
        return new Lecture().setName(lecturePostDto.getName())
                .setLectureDate(lecturePostDto.getLectureDate())
                .setZoomLink(lecturePostDto.getZoomLink())
                .setPresentationLink(lecturePostDto.getPresentationLink())
                .setLector(lector).setCohorts(cohorts);
    }

    public LecturePostPesponseDto convertToLecturePostResponseDto(Integer lectureId) {
        return new LecturePostPesponseDto(lectureId);
    }

    public void merge(Lecture lecture, LecturePatchDto lecturePatchDto, User lector) {
        if (lecturePatchDto.getName() != null) {
            lecture.setName(lecturePatchDto.getName());
        }
        if (lecturePatchDto.getLectureDate() != null) {
            lecture.setLectureDate(lecturePatchDto.getLectureDate());
        }
        if (lecturePatchDto.getZoomLink() != null) {
            lecture.setZoomLink(lecturePatchDto.getZoomLink());
        }
        if (lecturePatchDto.getPresentationLink() != null) {
            lecture.setPresentationLink(lecturePatchDto.getPresentationLink());
        }
        if (lecturePatchDto.getLectorId() != null) {
            lecture.setLector(lector);
        }
    }
}
