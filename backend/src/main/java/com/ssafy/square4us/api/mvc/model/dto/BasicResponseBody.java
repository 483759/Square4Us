package com.ssafy.square4us.api.mvc.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "BasicResponseBody")
public class BasicResponseBody<T> {
    @Schema(name = "Response Code", example = "200")
    private Integer statusCode = null;

    @Schema(name = "Response Message", example = "정상")
    private String message = null;

    @Schema(name = "Response Data")
    private T data;

    public BasicResponseBody(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static BasicResponseBody of(Integer statusCode, String message, Object data) {
        return new BasicResponseBody(statusCode, message, data);
    }
}
