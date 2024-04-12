package com.spring.portfolio.config;

import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.config.oauth.PrincipalDetails;
import com.spring.portfolio.config.oauth.PrincipalDetailsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PrincipalDetailsService principalDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Authentication 객체에서 username과 password를 꺼낸다.
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // UserDetailsService에서 UserDetails를 꺼낸다.
        PrincipalDetails userDetails =
                (PrincipalDetails) principalDetailsService.loadUserByUsername(username);

        // UserDetails의 password와 Authentication 객체의 password를 비교한다.
        if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // username, password 일치 시 세션에 user를 저장한다.
        httpSession.setAttribute("user", new MemberResponseDto(userDetails.getMember()));

        // username, password 일치 시 토큰을 생성한다.
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
