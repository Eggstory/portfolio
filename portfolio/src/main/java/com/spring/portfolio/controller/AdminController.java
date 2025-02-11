package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.service.AdminService;
import com.spring.portfolio.service.BoardService;
import com.spring.portfolio.service.MemberService;
import com.spring.portfolio.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final AdminService adminService;


    @GetMapping("")
    public String index(Model model, HttpServletRequest request,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        adminService.setNavName(model, request, principalDetails);

        return "admin/index";
    }

    @RequestMapping("/member/delete")
    @ResponseBody
    public String deleteMemberList(@RequestParam("memberIdx") String memberIdx){
        System.out.println(memberIdx);
        String[] arrIdx = memberIdx.split(",");
        for(String idx : arrIdx) {
            replyService.updateMemberNull(Long.valueOf(idx));
            boardService.updateMemberNull(Long.valueOf(idx));
            memberService.deleteMember(Long.valueOf(idx));
        }
        return "1";
    }

    @RequestMapping("/member/limit")
    @ResponseBody
    public String checkedLimitMemberList(@RequestParam("memberIdx") String memberIdx){
        System.out.println(memberIdx);
        String[] arrIdx = memberIdx.split(",");
        for(String idx : arrIdx) {
            replyService.updateIsDeleted(Long.valueOf(idx));
            memberService.limitMember(Long.valueOf(idx));
        }
        return "1";
    }

    @RequestMapping("/member/unLimit")
    @ResponseBody
    public String unLimitMember(@RequestParam("memberIdx") Long memberIdx){
        System.out.println(memberIdx);
        memberService.unLimitMember(memberIdx);
        return "1";
    }

    @RequestMapping("/member/checkedUnLimit")
    @ResponseBody
    public String checkedUnLimitMember(@RequestParam("memberIdx") String memberIdx){
        System.out.println(memberIdx);
        String[] arrIdx = memberIdx.split(",");
        for(String idx : arrIdx) {
            memberService.unLimitMember(Long.valueOf(idx));
        }
        return "1";
    }



    @RequestMapping("/board/delete")
    @ResponseBody
    public String deleteBoardList(@RequestParam("boardIdx") String boardIdx){
        System.out.println(boardIdx);
        String[] arrIdx = boardIdx.split(",");
        for(String idx : arrIdx) {
            boardService.deleteBoard(Long.valueOf(idx));
        }
        return "1";
    }

    @RequestMapping("/reply/delete")
    @ResponseBody
    public String deleteReplyList(@RequestParam("replyIdx") String replyIdx){
        System.out.println(replyIdx);
        String[] arrIdx = replyIdx.split(",");
        for(String idx : arrIdx) {
            replyService.changeReplyInfo(Long.valueOf(idx));
        }
        return "1";
    }


}
