package com.spring.portfolio.service;

import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.PrincipalDetails;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Member findMember = memberRepository.findByMemberMail(mail).orElseThrow(()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        // password 일치여부와 무관하게 username에 해당하는 user 존재 시 session을 생성
        // httpSession.setAttribute("user", new MemberResponseDto(findMember));

//        String password = passwordEncoder.encode(findMember.getMemberPw());
//        findMember.setMemberPw(password);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (findMember.getMemberRole().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getAuthority()));
        }else if(findMember.getMemberRole().equals("MASTER")){
            authorities.add(new SimpleGrantedAuthority(Role.MASTER.getAuthority()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getAuthority()));
        }
        return new PrincipalDetails(findMember);
    }
}
