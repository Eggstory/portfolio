package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.service.BoardService;
import com.spring.portfolio.service.MailService;
import com.spring.portfolio.service.MemberService;
import com.spring.portfolio.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ReplyService replyService;
    private final MailService mailService;

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
    @ResponseBody
    public ResponseEntity<?> joinAction(@RequestBody @Valid MemberRequestDto memberRequestDto, Errors errors) {

        memberService.registerMember(memberRequestDto,errors);

//        return "<script>alert('회원가입 완료'); location.href='/'</script>";
//        return "redirect:/";
        return ResponseEntity.ok((Map.of("message", "회원가입이 완료되었습니다.")));
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

        boolean checked;
        String visible = memberResponseDto.getVisible();
        if(visible.equals("Y")) {
            checked = true;
        } else {
            checked = false;
        }

        model.addAttribute("checked", checked);

        return "client/myInfo";
    }

//    @GetMapping("/api/myInfo")
//    @ResponseBody
//    public ResponseEntity<MemberResponseDto> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//
//        MemberResponseDto memberResponseDto = memberService.loadMemberInfo(principalDetails);
// //        List<BoardResponseDto> boardResponseDtos = boardService.loadBoardList(memberResponseDto.getMemberIdx());
//
//        return ResponseEntity.ok(memberResponseDto);
//    }

    @ResponseBody
    @PostMapping("/myInfo/editAction")
    public ResponseEntity<?> memberEditAction(@RequestBody Map<String, String> requestData) {
//    public ResponseEntity<?> memberEditAction(@RequestBody MemberRequestDto requestData) {
//    public String memberEditAction(@RequestBody Map<String, String> requestData) {

        String visible = requestData.get("visible");
        Long memberIdx = Long.valueOf(requestData.get("memberIdx"));
        String memberName = requestData.get("memberName");
        String introduction = requestData.get("introduction");

//        String visible = requestData.getVisible();
//        Long memberIdx = requestData.getMemberIdx();
//        String memberName = requestData.getMemberName();
//        String introduction = requestData.getIntroduction();

        MemberRequestDto memberRequestDto = new MemberRequestDto(memberName, memberIdx, introduction, visible);

        memberService.editMember(memberRequestDto);


        // 이전 페이지로 이동되는 코드
//        String referer = request.getHeader("Referer");
//        return ResponseEntity.ok(Map.of("message", "데이터가 성공적으로 저장되었습니다."));
        return ResponseEntity.ok(requestData);
//        return "<script>location.href='/myInfo'</script>";
    }

    @GetMapping("/changePw")
    public String findPw() {
        return "client/changePw";
    }

    @PostMapping("/myInfo/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@RequestBody Map<String,Long> request, HttpServletRequest sessionInfo) {

        Long idx = request.get("memberIdx");

        replyService.updateMemberNull(idx);
        boardService.updateMemberNull(idx);
        memberService.deleteMember(idx);
        HttpSession session = sessionInfo.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/resetPw")
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("memberMail");

        try {
            mailService.sendResetPasswordEmail(email);
            return ResponseEntity.ok("비밀번호 재설정 이메일이 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 재설정 이메일 전송이 실패했습니다.");
        }
    }

    @PostMapping("/myInfo/changePw")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request, HttpServletRequest sessionInfo) {
        String memberPw = request.get("memberPw");
        String newPw1 = request.get("newPw1");
        String newPw2 = request.get("newPw2");

        HttpSession session = sessionInfo.getSession();
        Long memberIdx = (Long) session.getAttribute("memberIdx");

        try {
            memberService.changePw(memberIdx, memberPw, newPw1, newPw2);
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 변경이 실패했습니다.");
        }
    }



}
