package com.spring.portfolio.config.oauth;

import com.spring.portfolio.config.PrincipalDetails;
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

//        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("memberMail", principal.getUsername());
        session.setAttribute("memberIdx", principal.getMember().getMemberIdx());
        response.sendRedirect("/");
        System.out.println("세션값 들어가나? "+principal.getUsername());
        System.out.println("authentication 의 getname : "+authentication.getName());
    }


}


