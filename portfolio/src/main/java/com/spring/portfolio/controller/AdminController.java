package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.service.AdminService;
import com.spring.portfolio.service.MemberService;
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
    private final AdminService adminService;


    @GetMapping("/")
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
            memberService.deleteMember(Long.valueOf(idx));
        }
        return "1";
    }


}
