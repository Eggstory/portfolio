package com.spring.portfolio.service;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepo;
    private final BCryptPasswordEncoder passwordEncoder;



    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto) {
        String encodePw = passwordEncoder.encode(memberRequestDto.getMemberPw());
        memberRequestDto.setMemberPw(encodePw);
        Member memberEntity = memberRequestDto.toEntity();
        memberRepo.save(memberEntity);
    }

    public void modify(Long MemberId) {

    }

    public void remove(Long MemberId) {

    }

    public MemberResponseDto login(String memberMail, String memberPw) throws Exception {

        Member member = memberRepo.findByMemberMail(memberMail)
                .orElseThrow(() -> new Exception("Member not found mail : " + memberMail));
        System.out.println(member.getMemberMail());
        String encodePw = passwordEncoder.encode(memberPw);
        boolean matches = passwordEncoder.matches(encodePw, member.getMemberPw());

        if(matches) {
            MemberResponseDto dto = new MemberResponseDto(member);
            return dto;
        }

        return null;
    }

    public String loadWriter(HttpSession request) {

        String memberMail = request.getAttribute("memberMail").toString();
        Member memberEntity = memberRepo.findByMemberMail(memberMail).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(memberEntity);

        return memberResponseDto.getMemberId();
    }

    public Long loadMemberIdx(HttpSession request) {

        String memberMail = request.getAttribute("memberMail").toString();
        Member memberEntity = memberRepo.findByMemberMail(memberMail).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(memberEntity);

        return memberResponseDto.getMemberIdx();
    }
}
