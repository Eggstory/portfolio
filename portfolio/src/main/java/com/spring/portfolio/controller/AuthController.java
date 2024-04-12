package com.spring.portfolio.controller;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

//    public ResponseEntity<MemberResponseDto> signin(@RequestBody MemberRequestDto memberRequestDto) {
//        return ResponseEntity.ok(memberService.signin(memberRequestDto));
//    }
}
