package com.spring.portfolio.controller;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "client/index";
    }

    @GetMapping("/login")
    public String login() {
        return "client/login";
    }

    @PostMapping("/loginAction")
    public String loginAction(String memberMail, String memberPw, HttpServletRequest request) throws Exception {

        MemberResponseDto dto = memberService.login(memberMail, memberPw);
        if(dto.getMemberMail() == null || dto.getMemberMail().equals("")) {
            System.out.println("로그인 실패");
            return "redirect:/login";
        }

        request.getSession().invalidate();
        HttpSession session = request.getSession();
        session.setAttribute("memberMail", dto.getMemberMail());
        System.out.println(session.getAttribute("memberMail"));

        return "redirect:/";
    }

    @GetMapping("/join")
    public String join() {
        return "client/join";
    }

    @PostMapping("/joinAction")
    public String joinAction(MemberRequestDto memberRequestDto) {

        memberService.registerMember(memberRequestDto);

        return "client/index";
    }

}
