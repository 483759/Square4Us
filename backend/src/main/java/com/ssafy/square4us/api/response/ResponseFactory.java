package com.ssafy.square4us.api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
    public static ResponseEntity<? extends BasicResponseBody> Ok(){
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponseBody.of(200, "성공"));
    }

    public static ResponseEntity<? extends BasicResponseBody> NotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BasicResponseBody.of(404, "찾을 수 없음"));
    }

    public static ResponseEntity<? extends BasicResponseBody> Forbidden(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BasicResponseBody.of(403, "금지됨"));
    }

    public static ResponseEntity<? extends BasicResponseBody> Unauthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BasicResponseBody.of(401, "권한이 없음"));
    }

    public static ResponseEntity<? extends BasicResponseBody> NoContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BasicResponseBody.of(204, "존재하지 않음"));
    }

    public static ResponseEntity<? extends BasicResponseBody> Conflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BasicResponseBody.of(409, "충돌"));
    }
}
