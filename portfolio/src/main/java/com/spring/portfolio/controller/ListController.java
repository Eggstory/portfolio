package com.spring.portfolio.controller;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.BoardResponseDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.dto.ReplyResponseDto;
import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.entity.Reply;
import com.spring.portfolio.service.AdminService;
import com.spring.portfolio.service.ListService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class ListController {
    private final ListService listService;
    private final AdminService adminService;

    @GetMapping("/list/member")
    public String getMemberList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        adminService.setNavName(model, request, principalDetails);
        Page<Member> paging = listService.loadMemberList(keyword, page);
        List<MemberResponseDto> list = new ArrayList<>();
        for (Member entity : paging) {
            list.add(new MemberResponseDto(entity));
        }

        if (keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("type", "member");
        model.addAttribute("list", list);
        return "admin/index";
    }

    @GetMapping("/list/limitMember")  // 맴버 리스트 찾기
    public String getLimitMemberList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     HttpServletRequest request,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails) {
        adminService.setNavName(model, request, principalDetails);
        Page<Member> paging = listService.loadLimitMemberList(keyword, page);
        List<MemberResponseDto> list = new ArrayList<>();
        for (Member entity : paging) {
            list.add(new MemberResponseDto(entity));
        }

        if (keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("type", "limitMember");
        model.addAttribute("list", list);
        return "admin/index";
    }

    @GetMapping("/list/board")  // 게시판 리스트 찾기
    public String loadBoardList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        adminService.setNavName(model, request, principalDetails);
        Page<Board> paging = listService.loadBoardList(keyword, page);
        List<BoardResponseDto> list = new ArrayList<>();
        for (Board entity : paging) {
            list.add(new BoardResponseDto(entity));
        }


        if (keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("type", "board");
        model.addAttribute("list", list);
        return "admin/index";
    }

    @GetMapping("/list/reply")
    public String loadReplyList(Model model, @RequestParam(value = "search", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                HttpServletRequest request,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        adminService.setNavName(model, request, principalDetails);
        Page<Reply> paging = listService.findReplyList(keyword, page);
        List<ReplyResponseDto> list = new ArrayList<>();
        for (Reply entity : paging) {
            list.add(new ReplyResponseDto(entity));
        }

        if (keyword == null) {
            model.addAttribute("keyword", "");
        } else {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("type", "reply");
        model.addAttribute("list", list);
        return "admin/index";
    }

}
