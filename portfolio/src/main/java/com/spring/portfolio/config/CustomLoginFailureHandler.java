package com.spring.portfolio.config;

import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

//    private static final int MAX_ATTEMPT = 5;
//    private static final int LOCK_TIME = (1000 * 60) * 180; // 3시간동안 잠금

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }



//        response.sendRedirect("/login");
//        String memberMail = request.getParameter("memberMail");
//        String password = request.getParameter("memberPw");
//        String error = exception.getMessage();

//        Member member = memberRepository.findByMemberMail(memberMail).get();

//        if(exception.getClass() == BadCredentialsException.class) {
//            int errorCode = badCredential(member);
//            response.sendError(401, "BadCredential");
//        }
//        else if (exception.getClass() == LockedException.class) {
//            System.out.println(exception.getClass());
//            int statusCode = lockControl(response, member);
//            response.sendError(statusCode, "lockControl");
//        }
//        response.sendRedirect("/error/4xx");
    }

//    private int lockControl(HttpServletResponse response, Member member) {
//        boolean unlock = unlock(member);
//        if(unlock) {
//            return 302;
//        }
//        long l = (member.getLockTime().getTime() - System.currentTimeMillis()) / 1000;
//        String lockTime = (l/3600) + ":" + (l/60) + ":" + l;
//        Cookie cookie = new Cookie("lockTime", lockTime);
//        cookie.setPath("/login");
//        response.addCookie(cookie);
//        return 402;
//    }

//    private int badCredential(Member member) throws IOException {
//
//        if (member.getMemberLock().equals("active")) {
//            member.addFailedAttempt(true);
//            if (member.getFailedAttempt() >= MAX_ATTEMPT) { // 최대 시도 이상으로 틀렸을경우
//                member.updateLock("LOCKED",new LocalDateTime(System.currentTimeMillis() + LOCK_TIME));
//            }
//        }
//
//
//        return 403;
//    }

//    public boolean unlock(Member member) {
//        long memberLockTime = member.getLockTime().getTime();
//        long currentTimeMillies = System.currentTimeMillis();
//
//        if(memberLockTime <= currentTimeMillies) {
//            member.addFailedAttempt(false);
//            member.updateLock("ACTIVE",null);
//            return true;
//        }
//        return false;
//
//
//    }



