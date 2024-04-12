package com.spring.portfolio.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        HttpSession session = request.getSession();
        session.setAttribute("memberMail", request.getParameter("memberMail"));
        session.setAttribute("memberIdx", request.getParameter("memberIdx"));
        session.setAttribute("greeting",authentication.getName() + "님 반갑습니다.");
        response.sendRedirect("/");

    }
}
