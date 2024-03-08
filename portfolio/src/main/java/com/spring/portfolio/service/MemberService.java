package com.spring.portfolio.service;

import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepo;


    public void registerMember(MemberRequestDto memberRequestDto) {
        Member memberEntity = memberRequestDto.toEntity();
        memberRepo.save(memberEntity);
    }

    public void modify(Long MemberId) {

    }

    public void remove(Long MemberId) {

    }

    public MemberResponseDto login(String memberMail, String memberPw) throws Exception {
        Member member = memberRepo.findByMailAndPw(memberMail, memberPw)
                .orElseThrow(() -> new Exception("Member not found mail : " + memberMail));

        MemberResponseDto dto = new MemberResponseDto(member);

        return dto;
    }



}
