package hh.crossreview.dto.lecture;

import hh.crossreview.dto.homework.HomeworkDto;
import hh.crossreview.dto.user.UserDto;
import hh.crossreview.entity.Cohort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Schema (name = "LectureDto", requiredProperties = {"lectureId",
        "name",
        "lectureDate",
        "zoomLink",
        "presentationLink",
        "lector"})
public class LectureDto implements Serializable {
    @NotNull
    private Integer lectureId;
    @NotNull
    private String name;
    @NotNull
    private Date lectureDate;
    private String zoomLink;
    private String presentationLink;
    @NotNull(message = "Field 'teacherId' couldn't be empty")
    private UserDto lector;

    public Integer getLectureId() {
        return lectureId;
    }

    public String getName() {
        return name;
    }

    public Date getLectureDate() {
        return lectureDate;
    }

    public String getZoomLink() {
        return zoomLink;
    }

    public String getPresentationLink() {
        return presentationLink;
    }

    public UserDto getLector() {
        return lector;
    }


    public LectureDto setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
        return this;
    }

    public LectureDto setName(String name) {
        this.name = name;
        return this;
    }

    public LectureDto setLectureDate(Date lectureDate) {
        this.lectureDate = lectureDate;
        return this;
    }

    public LectureDto setZoomLink(String zoomLink) {
        this.zoomLink = zoomLink;
        return this;
    }

    public LectureDto setPresentationLink(String presentationLink) {
        this.presentationLink = presentationLink;
        return this;
    }

    public LectureDto setLector(UserDto lector) {
        this.lector = lector;
        return this;
    }

}