package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.dto.ReplyResponseDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final MailService mailService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "client/login";
    }

    @GetMapping("/join")
    public String join() {
        return "client/join";
    }

    @PostMapping("/joinAction")
    @ResponseBody
    public ResponseEntity<?> joinAction(@RequestBody @Valid MemberRequestDto memberRequestDto, Errors errors) {

        memberService.registerMember(memberRequestDto, errors);

        // json 말고 html로 보냈을때 이걸 썼었는데
//        return "<script>alert('회원가입 완료'); location.href='/'</script>";
        return ResponseEntity.ok((Map.of("message", "회원가입이 완료되었습니다.")));
    }

    @GetMapping("/myInfo")
    public String myInfo(Model model,
                         @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        MemberResponseDto memberResponseDto = memberService.loadMemberInfo(principalDetails);
        List<BoardResponseDto> boardResponseDtos = boardService.loadBoardList(memberResponseDto.getMemberIdx());
        List<ReplyResponseDto> replyResponseDtos = replyService.loadReplyList(memberResponseDto.getMemberIdx());

        model.addAttribute("member", memberResponseDto);
        model.addAttribute("board", boardResponseDtos);
        model.addAttribute("reply", replyResponseDtos);

        boolean checked;
        String visible = memberResponseDto.getVisible();
        if (visible.equals("Y")) {
            checked = true;
        } else {
            checked = false;
        }

        model.addAttribute("checked", checked);
        model.addAttribute("type1", "board");
        model.addAttribute("type2", "reply");

        return "client/myInfo";
    }

    @ResponseBody
    @PostMapping("/myInfo/editAction")
    public ResponseEntity<?> memberEditAction(@RequestBody Map<String, String> requestData) {

        String visible = requestData.get("visible");
        Long memberIdx = Long.valueOf(requestData.get("memberIdx"));
        String memberName = requestData.get("memberName");
        String introduction = requestData.get("introduction");

        MemberRequestDto memberRequestDto = new MemberRequestDto(memberName, memberIdx, introduction, visible);

        memberService.editMember(memberRequestDto);

//        return ResponseEntity.ok(Map.of("message", "데이터가 성공적으로 저장되었습니다."));
        return ResponseEntity.ok(requestData);
    }

    @GetMapping("/changePw")
    public String findPw() {
        return "client/changePw";
    }

    @PostMapping("/myInfo/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(@RequestBody Map<String, Long> request, HttpServletRequest sessionInfo) {

        Long idx = request.get("memberIdx");

        replyService.updateMemberNull(idx);
        boardService.updateMemberNull(idx);
        memberService.deleteMember(idx);
        HttpSession session = sessionInfo.getSession(false);
        if (session != null) {
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
