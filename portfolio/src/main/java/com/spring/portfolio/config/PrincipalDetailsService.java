package com.spring.portfolio.config;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member findMember = memberRepository.findByMemberMail(email).orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (findMember.getMemberRole().getAuthority().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getAuthority()));
        } else if (findMember.getMemberRole().getAuthority().equals("MASTER")) {
            authorities.add(new SimpleGrantedAuthority(Role.MASTER.getAuthority()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getAuthority()));
        }
        return new PrincipalDetails(findMember);
    }
}
