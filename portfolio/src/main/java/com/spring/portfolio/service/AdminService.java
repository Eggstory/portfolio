package com.spring.portfolio.service;

import com.spring.portfolio.config.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberService memberService;

    public void setNavName(Model model, HttpServletRequest request, PrincipalDetails principalDetails) {

        if(principalDetails == null || principalDetails.equals("")) {
            String sessionMemberName = (String) request.getSession().getAttribute("memberMail");
            String name = memberService.loadWriter(sessionMemberName);
            model.addAttribute("name",name);
        } else  {
            String principalMemberName = principalDetails.getMember().getMemberName();
            model.addAttribute("name",principalMemberName);
        }


    }
}
