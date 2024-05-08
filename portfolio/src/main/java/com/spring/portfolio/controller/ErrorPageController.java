package com.spring.portfolio.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error/4xx")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        return "error/4xx.html";
    }

    @GetMapping("/error/403")
    public String errorPage403(HttpServletRequest request, HttpServletResponse response) {
        return "error/4xx.html";
    }

    @GetMapping("/error/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        return "error/500.html";
    }

}
