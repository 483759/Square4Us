package com.ssafy.square4us.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "MemberLoginPostResponse")
public class MemberLoginPostRes extends BasicResponseBody {
    @Schema(name = "JWT Authentication Token")
    String accessToken;

    public static MemberLoginPostRes of(Integer statusCode, String message, String accessToken) {
        MemberLoginPostRes res = new MemberLoginPostRes(accessToken);
        res.setStatusCode(statusCode);
        res.setMessage(message);
        return res;
    }
}
