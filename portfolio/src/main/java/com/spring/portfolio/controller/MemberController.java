package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.service.BoardService;
import com.spring.portfolio.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

//    @GetMapping("/login")
//    public String login() {
//        return "client/login";
//    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "client/login";
    }

//     spring security 때문에 여기 컨트롤러 안탐
//    @PostMapping("/loginAction")
//    public String loginAction(String memberMail, String memberPw, HttpServletRequest request) throws Exception {
//
//        MemberResponseDto dto = memberService.login(memberMail, memberPw);
//        if(dto.getMemberMail() == null || dto.getMemberMail().equals("")) {
//            return "redirect:/login";
//        }
//
//        request.getSession().invalidate();
//        HttpSession session = request.getSession();
//        session.setAttribute("memberMail", dto.getMemberMail());
//
//        System.out.println("테스트4");
//        return "redirect:/";
//    }

    @GetMapping("/join")
    public String join() {
        return "client/join";
    }

    @PostMapping("/joinAction")
    public String joinAction(@Valid MemberRequestDto memberRequestDto, Errors errors) {

        memberService.registerMember(memberRequestDto,errors);

        return "redirect:/";
    }


//    // 뭔코드인지 궁금해서 가져와봄
//    @GetMapping("/form/loginInfo")
//    @ResponseBody
//    public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
//
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//        Member member = principal.getMember();
//        System.out.println(member);
//        //Member(id=2, username=11, password=$2a$10$m/1Alpm180jjsBpYReeml.AzvGlx/Djg4Z9/JDZYz8TJF1qUKd1fW, email=11@11, role=ROLE_USER, createTime=2022-01-30 19:07:43.213, provider=null, providerId=null)
//
//        Member member1 = principalDetails.getMember();
//        System.out.println(member1);
//        //Member(id=2, username=11, password=$2a$10$m/1Alpm180jjsBpYReeml.AzvGlx/Djg4Z9/JDZYz8TJF1qUKd1fW, email=11@11, role=ROLE_USER, createTime=2022-01-30 19:07:43.213, provider=null, providerId=null)
//        //Member == user1
//
//        return member.toString();
//    }

    @GetMapping("/myInfo")
    public String myInfo(Model model,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        MemberResponseDto memberResponseDto = memberService.loadMemberInfo(principalDetails);
        List<BoardResponseDto> boardResponseDtos = boardService.loadBoardList(memberResponseDto.getMemberIdx());

        model.addAttribute("member", memberResponseDto);
        model.addAttribute("board", boardResponseDtos);

        return "client/myInfo";
    }

    @GetMapping("/findId")
    public String findId() {
        return "client/findId";
    }
    @GetMapping("/findPw")
    public String findPw() {
        return "client/findPw";
    }




}
