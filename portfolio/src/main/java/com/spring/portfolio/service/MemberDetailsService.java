package com.spring.portfolio.service;

import com.spring.portfolio.dto.PrincipalDetails;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Member findMember = memberRepository.findByMemberMail(mail).orElseThrow(()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        return new PrincipalDetails(findMember);
    }
}
