package com.spring.portfolio.service;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepo;
    private final BCryptPasswordEncoder passwordEncoder;



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

}
