package com.spring.portfolio.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {


    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> hanlderCustomException(CustomException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getStatus(), e.getErrorCode().getReason())
                , e.getErrorCode().getCode());
        // HttpStatus.valueOf : 지정된 숫자 값으로 HttpStatus enum 상수를 반환합니다.
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getBindingResult().getFieldErrors().get(0),
                // .get(0).getField() + "가  "+ e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
        // e.getMessage()는 전체 오류 메시지가 하나의 문자열로 반환
        // e.getBindingResult()는 각각의 데이터가 바인딩된 결과를 하나씩 반환
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
//        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_TITLE_IS_EMPTY);
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected String handleConstraintViolationException(ConstraintViolationException e, Model model) {
        ErrorResponse response = new ErrorResponse(ErrorCode.BOARD_TITLE_IS_EMPTY);
        ResponseEntity<ErrorResponse> body = ResponseEntity.status(response.getStatus()).body(response);
        model.addAttribute("model", body.getBody().getReason());
        System.out.println(body.getBody());
        return "error/4xx.html";
    }

//    @ExceptionHandler({RuntimeException.class})
//    protected ResponseEntity<String> handlerEtcException(RuntimeException e) {
//
//        String message = e.getMessage();
//
//        return new ResponseEntity<>("에러코드("+HttpStatus.BAD_REQUEST+"): "+e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({RuntimeException.class})
    protected String handlerEtcException(RuntimeException e, Model model) {

        ResponseEntity<String> responseEntity = new ResponseEntity<>("에러코드(" + HttpStatus.BAD_REQUEST + "): " + e.getMessage(), HttpStatus.BAD_REQUEST);

//        model.addAttribute("ResponseEntity", responseEntity);
//        System.out.println(responseEntity.getStatusCode());
//        System.out.println(responseEntity.getBody());
//        System.out.println(e.getMessage());
//        System.out.println(e);
        String except = e.toString();
        String[] split = except.split(":");
        System.out.println(split[0]);
        if(split[0].contains("NullPointerException")) {
            model.addAttribute("model", "로그인을 해주세요.");
        }


        return "error/500.html";
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>("이메일 중복 오류", ErrorCode.DUPLICATE_MEMBER_MAIL.getCode());
    }




//    MissingServletRequestParameterException.class: request parameter가 없을 때 에러를 리턴한다.
//    MissingRequestHeaderException.class: request header가 없을 때 에러를 리턴한다.
//    MethodArgumentNotValidException.class: request body의 데이터가 유효하지 않을 때 에러를 리턴한다.
//    NoHandlerFoundException.class: 404 error를 리턴한다.

}
