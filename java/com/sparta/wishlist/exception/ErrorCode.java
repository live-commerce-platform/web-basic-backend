package com.sparta.wishlist.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 404 Not Found
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품이 존재하지 않습니다."),

    // 500 Internal Server Error
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "요청을 처리하는 중 서버에서 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    public int getCode() {
        return status.value();
    }
}