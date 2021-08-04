package com.ssafy.square4us.api.response;

import com.ssafy.square4us.api.mvc.model.entity.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "All Study List Get Response")
public class StudyListGetRes extends BasicResponseBody {
    @Schema(name = "스터디 목록")
    List<Study> studyList;

    public static StudyListGetRes of(Integer statusCode, String message, List<Study> studyList) {
        StudyListGetRes res = new StudyListGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setStudyList(studyList);
        return res;
    }
}
