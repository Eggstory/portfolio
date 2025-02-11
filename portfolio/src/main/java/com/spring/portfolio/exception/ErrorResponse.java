package com.spring.portfolio.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();    // 현재시간
    private final HttpStatus code;    // BAD_REQUEST 같은거
    private final int status;    // 400, 401, 404, 500 이런거
    private final String reason;       // 문제 지적
    private List<FieldError> errors;    // 상세 에러 메시지 (에러가 하나 이상일 수 있기 때문에 List 객체 사용)


    @Getter
    @Setter
    @NoArgsConstructor
    public static class FieldError {
        private String field;           // 해당 필드값
        private String message;         // 해당 오류시 뜰 말
    }


    @Builder
    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.reason = errorCode.getReason();
    }

    @Builder
    public ErrorResponse(ErrorCode code, List<FieldError> errors) {
        this.code = code.getCode();
        this.status = code.getStatus();
        this.reason = code.getReason();
        this.errors = errors;
    }
}
