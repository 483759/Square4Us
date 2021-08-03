package com.ssafy.square4us.api.response;

import com.ssafy.square4us.api.mvc.model.entity.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Study Create Post Response")
public class StudyCreatePostRes extends BasicResponseBody {
    @Schema(name = "스터디 ID")
    Long id;

    public static StudyCreatePostRes of(Integer statusCode, String message, Study study) {
        StudyCreatePostRes res = new StudyCreatePostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(study.getId());
        return res;
    }
}
