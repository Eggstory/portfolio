package com.spring.portfolio.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

//    private final TokenProvider tokenProvider;
    private static final String URI = "/auth/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // accessToken, refreshToken 발급
//        String accessToken = tokenProvider.generateAccessToken(authentication);
//        tokenProvider.generateRefreshToken(authentication, accessToken);
//
//        // 토큰 전달을 위한 redirect
//        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
//                .queryParam("accessToken", accessToken)
//                .build().toUriString();
//
//        response.sendRedirect(redirectUrl);

        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        HttpSession session = request.getSession();
        session.setAttribute("memberMail", request.getParameter("memberMail"));
        session.setAttribute("memberIdx", request.getParameter("memberIdx"));
        session.setAttribute("greeting",authentication.getName() + "님 반갑습니다.");
        response.sendRedirect("/");
        System.out.println("세션값 들어가나? "+request.getParameter("memberMail"));
    }


}


