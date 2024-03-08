package com.spring.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board")
    public String main() {
        return "client/board";
    }

    @GetMapping("/boardList")
    public String signUp() {
        return "client/boardList";
    }

}
