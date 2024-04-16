package com.spring.portfolio.controller;

import com.spring.portfolio.dto.ReplyRequestDto;
import com.spring.portfolio.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/board/replyAction")
    public String replyAction(ReplyRequestDto dto, HttpServletRequest request) {

        replyService.saveReply(dto);

        // 이전 페이지로 이동되는 코드
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/board/replyUpdate")
    public String replyUpdate(ReplyRequestDto dto, HttpServletRequest request) throws Exception {

        replyService.updateReply(dto);

//        // 이전 페이지로 이동되는 코드
//        String referer = request.getHeader("Referer");
//        return "redirect:"+ referer;

        return "redirect:/board";
    }

    @PostMapping("/board/replyDelete")
    public String replyDelete(Long replyIdx) {

        replyService.deleteReply(replyIdx);

        return "redirect:/board";
    }

}
