package com.ssafy.square4us.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 스터디 생성 API 요청에 필요한 Request Body
 * [POST] /api/study
 */
@Getter
@Setter
@ToString
@Schema(description = "Meeting Create Post Request")
public class MeetingCreatePostReq {

    @Schema(name = "studyid", example = "1")
    Long studyId;

    @Schema(name = "maxpeople", example = "15")
    Integer maxPeople;

    @Schema(name = "thumbnailname", example = "sample.jpg")
    String thumbnailName;

    @Schema(name = "thumbnailpath", example = "/test/sample")
    String thumbnailPath;
}
