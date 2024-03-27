package com.spring.portfolio.controller;

import com.spring.portfolio.dto.ReplyRequestDto;
import com.spring.portfolio.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/board/replyAction")
    public String ReplyAction(ReplyRequestDto dto) {

        replyService.saveReply(dto);

        return "redirect:/board";
    }



}
