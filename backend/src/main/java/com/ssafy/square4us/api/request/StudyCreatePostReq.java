package com.ssafy.square4us.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 스터디 생성 API 요청에 필요한 Request Body
 * [POST] /api/study
 * */
@Getter
@Setter
@ToString
@Schema(description = "Study Create Post Request")
public class StudyCreatePostReq {
	@Schema(name="category", example="ALGORITHM")
	String category;
	@Schema(name="name", example="모르고리즘")
	String name;
}
