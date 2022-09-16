package com.mungta.noticeservice.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum ApiStatus {
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -1, "예상치 못한 에러가 발생하였습니다."),
    //-200 Notice 에러
    NOT_EXIST_NOTICE(HttpStatus.INTERNAL_SERVER_ERROR,-201, "공지사항이 존재하지 않습니다."),
    ;
    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ApiStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static ApiStatus of(String message) {
        return Arrays.stream(ApiStatus.values())
                .filter(apiStatus -> apiStatus.getMessage().equals(message))
                .findFirst()
                .orElse(null);
    }
}
