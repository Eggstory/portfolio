package com.spring.portfolio.controller;

import com.spring.portfolio.dto.ReplyRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReplyController {

    @GetMapping("/board/replyAction")
    public String ReplyAction(ReplyRequestDto requestDto) {



        return "redirect:/board";
    }



}
