package com.spring.portfolio.config;

import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private static final int MAX_ATTEMPT = 5;
    private static final int LOCK_TIME = (1000 * 60) * 180; // 3시간동안 잠금

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

     /*   String memberMail = request.getParameter("memberMail");
        String password = request.getParameter("memberPw");
        String error = exception.getMessage();

        Member member = memberRepository.findByMemberMail(memberMail).get();

        if(exception.getClass() == BadCredentialsException.class) {
            int errorCode = badCredential(member);
            response.sendError(errorCode, "BadCredential");
        } else if (exception.getClass() == LockedException.class) {
            System.out.println(exception.getClass());
            int statusCode = lockControl(response, member);
            response.sendError(statusCode, "lockControl");
        }
//        response.sendRedirect("/denied");
    }

    private int lockControl(HttpServletResponse response, Member member) {
        boolean unlock = unlock(member);
        if(unlock) {
            return 302;
        }
        long l = (member.getLockTime().getTime() - System.currentTimeMillis()) / 1000;
        String lockTime = (l/3600) + ":" + (l/60) + ":" + l;
        Cookie cookie = new Cookie("lockTime", lockTime);
        cookie.setPath("/login");
        response.addCookie(cookie);
        return 402;
    }

    private int badCredential(Member member) throws IOException {

        if (member.getMemberLock().equals("active")) {
            member.addFailedAttempt(true);
            if (member.getFailedAttempt() >= MAX_ATTEMPT) { // 최대 시도 이상으로 틀렸을경우
                member.updateLock("LOCKED",new LocalDateTime(System.currentTimeMillis() + LOCK_TIME));
            }
        }


        return 403;
    }

    public boolean unlock(Member member) {
        long memberLockTime = member.getLockTime().getTime();
        long currentTimeMillies = System.currentTimeMillis();

        if(memberLockTime <= currentTimeMillies) {
            member.addFailedAttempt(false);
            member.updateLock("ACTIVE",null);
            return true;
        }
        return false;

      */
    }
}


