package com.ssafy.square4us.api.mvc.model.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
    public static ResponseEntity<? extends BasicResponseBody> ok() {
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseBody.of(200, "성공", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponseBody.of(201, "생성됨", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BasicResponseBody.of(404, "찾을 수 없음", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BasicResponseBody.of(403, "금지됨", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "권한이 없음", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BasicResponseBody.of(204, "존재하지 않음", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> conflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BasicResponseBody.of(409, "충돌", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> internalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponseBody.of(500, "서버 에러", new EmptyDto(true)));
    }

    public static ResponseEntity<? extends BasicResponseBody> serviceUnavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(BasicResponseBody.of(503, "요청 처리 불가", new EmptyDto(true)));
    }
}
