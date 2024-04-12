package com.spring.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저명이 중복됩니다"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러가 발생하였습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 토큰이 없습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT 서명이 잘못되었습니다.");



    private HttpStatus httpStatus;
    private String message;

}
