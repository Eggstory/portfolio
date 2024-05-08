package com.spring.portfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BOARD_TITLE_IS_EMPTY(HttpStatus.NOT_FOUND,400,"게시글 제목을 정해주십시오."),
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST,401,"게시판을 불러 올 수 없습니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.BAD_REQUEST,401,"비밀번호가 일치 하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,401,"유효하지 않은 토큰입니다."),
    AUTHORIZATION_EXCEPTION(HttpStatus.BAD_REQUEST,403,"접근할 수 있는 권한이 없습니다."),
    POST_IS_EMPTY(HttpStatus.BAD_REQUEST,404,"해당 게시글이 존재 하지 않습니다."),
    COMMENT_IS_EMPTY(HttpStatus.BAD_REQUEST,404,"해당 댓글이 존재 하지 않습니다."),
    USER_IS_NOT_EXIST(HttpStatus.BAD_REQUEST,404,"사용자가 존재 하지 않습니다."),
    REQUEST_IS_EMPTY(HttpStatus.BAD_REQUEST,404,"요청이 존재하지 않습니다."),
    PAGE_IS_NOT_EXIST(HttpStatus.BAD_REQUEST,404,"요청하신 페이지 내역이 존재하지 않습니다."),
    USERNAME_IS_EXIST(HttpStatus.BAD_REQUEST,409,"이미 등록된 userName 입니다."),
    DUPLICATE_MEMBER_MAIL(HttpStatus.CONFLICT, 409, "이미 등록된 MemberMail이 있습니다.");

//    //Common -> http 요청시 발생할만한 예외
//    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Common-002", " Invalid Input Value"),
//    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Common-002", " Invalid Http Method"),
//    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST,"Common-003", " Entity Not Found"),
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Common-004", "Server Error"),
//    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST,"Common-005", " Invalid Type Value"),
//    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Common-006", "Access is Denied"),
//
//    //Member Validation
//    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "Member-001", "Email is Duplication"),
//    LOGIN_INPUT_INVALID(HttpStatus.BAD_REQUEST, "Member-002", "Login input is invalid"),
//
//    //room Validation
    
    // 위에 주석들 쓸려면 StatusCode 파라미터가 문자열이기 때문에 int 대신 String을 넣어야할듯
//  private final String StatusCode;

//    OK(HttpStatus.OK, "성공"),
//    LECTURE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당 강의를 찾을 수 없습니다."),
//    LECTURE_STEP_DUPLICATED(HttpStatus.BAD_REQUEST, "중북되는 섹션의 강의가 존재합니다."),
//    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 퀴즈를 찾을 수 없습니다."),
//    QUIZ_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당 퀴즈의 답안 인덱스가 잘못된 값을 갖고 있습니다."),
//    EDUCATION_PROGRESS_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "유저의 교육 진도 정보가 조회되지 않습니다."),
//    EDUCATION_PROGRESS_BAD_REQUEST(HttpStatus.BAD_REQUEST, "이전 진도를 모두 완료해야 수강할 수 있습니다."),
//    EDUCATION_QUIZ_FAIL(HttpStatus.BAD_REQUEST, "점수가 낮아 퀴즈 테스트를 실패했습니다."),
//    EDUCATION_POSTURE_FAIL(HttpStatus.BAD_REQUEST, "점수가 낮아 자세실습 테스트를 실패했습니다."),

//    //400 BAD_REQUEST 잘못된 요청
//    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
//
//    //404 NOT_FOUND 잘못된 리소스 접근
//    DISPLAY_NOT_FOUND(404, "존재하지 않는 전시회 ID 입니다."),
//    FAIR_NOT_FOUND(404, "존재하지 않는 박람회 ID 박람회입니다."),
//    FESTIVAL_NOT_FOUND(404, "존재하지 않는 페스티벌 ID 페스티벌입니다."),
//    SAVED_DISPLAY_NOT_FOUND(404, "저장하지 않은 전시회입니다."),
//    SAVED_FAIR_NOT_FOUND(404, "저장하지 않은 박람회입니다."),
//    SAVED_FESTIVAL_NOT_FOUND(404, "저장하지 않은 페스티벌입니다."),
//
//    //409 CONFLICT 중복된 리소스
//    ALREADY_SAVED_DISPLAY(409, "이미 저장한 전시회입니다."),
//    ALREADY_SAVED_FAIR(409, "이미 저장한 박람회입니다."),
//    ALREADY_SAVED_FESTIVAL(409, "이미 저장한 페스티벌입니다."),
//
//    //500 INTERNAL SERVER ERROR
//    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 팀에 연락주세요!");

//    /* 200 OK : 성공 */
//    SUCCESS(200, "OK", "요청에 성공하였습니다."),
//
//    /* 400 BAD_REQUEST : 잘못된 요청 */
//    INVALID_INPUT_VALUE(400, "BAD_REQUEST", "입력값이 올바르지 않습니다."),
//    BAD_REQUEST(400, "BAD_REQUEST","잘못된 요청입니다."),
//
//    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
//    UNAUTHENTICATED_USERS(401, "UNAUTHORIZED","인증이 필요합니다."),
//
//    /* 403 FORBIDDEN : 접근권한 없음 */
//    ACCESS_DENIED(403, "FORBIDDEN","접근이 거부되었습니다."),
//
//    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
//    MEMBER_NOT_FOUND(404, "NOT_FOUND","해당 유저 정보를 찾을 수 없습니다."),
//    RESOURCE_NOT_FOUND(404, "NOT_FOUND","해당 정보를 찾을 수 없습니다."),
//
//    /* 405 METHOD_NOT_ALLOWED : 지원하지 않는 HTTP Method */
//    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED","허용되지 않은 요청입니다."),
//
//    /* 409 CONFLICT : 데이터 중복 */
//    DUPLICATE_RESOURCE(409, "CONFLICT","데이터가 이미 존재합니다"),
//
//    /* 500 INTERNAL_SERVER_ERROR */
//    SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "예기치 못한 오류가 발생하였습니다.")



    private final HttpStatus code;
    private final int status;
    private final String reason;




//    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
//    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
//    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저명이 중복됩니다"),
//    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러가 발생하였습니다"),
//    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 토큰이 없습니다."),
//    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT 서명이 잘못되었습니다.");

//    private HttpStatus httpStatus;
//    private String message;

}
