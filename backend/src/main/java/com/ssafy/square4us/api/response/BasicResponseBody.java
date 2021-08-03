package com.ssafy.square4us.api.response;

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
public class BasicResponseBody {
    @Schema(name = "Response Code", example = "200")
    Integer statusCode = null;

    @Schema(name = "Response Message", example = "정상")
    String message = null;

    public BasicResponseBody(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public static BasicResponseBody of(Integer statusCode, String message) {
        return new BasicResponseBody(statusCode, message);
    }
}
