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

// 시큐리티에서 설정에서 LoginProcessingUrl("/loginAction");
// "/loginAction" 요청이 오면 자동으로 UserDetailsService 타입으로 loC 되어있는  loadUserByUsername 함수가 실행된다.!
// Authentication 객체로 만들어준다

@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    //시큐리티 session => Authentication => UserDetails
    // 여기서 리턴 된 값이 Authentication 안에 들어간다.(리턴될때 들어간다.)
    // 그리고 시큐리티 session 안에 Authentication 이 들어간다.
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        Member findMember = memberRepository.findByMemberName(username).orElseThrow(()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        Member findMember = memberRepository.findByMemberMail(email).orElseThrow(()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

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
