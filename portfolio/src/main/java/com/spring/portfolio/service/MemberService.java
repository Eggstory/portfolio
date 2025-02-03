package com.spring.portfolio.service;

import com.spring.portfolio.config.PrincipalDetails;
import com.spring.portfolio.dto.MemberRequestDto;
import com.spring.portfolio.dto.MemberResponseDto;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto, Errors errors) {
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

        return memberResponseDto.getMemberName();
    }

    public String loadWriter(String memberMail) {

        Member memberEntity = memberRepo.findByMemberMail(memberMail).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(memberEntity);

        return memberResponseDto.getMemberName();
    }

    public Long loadMemberIdx(HttpSession request) {

        String memberMail = request.getAttribute("memberMail").toString();
        Member memberEntity = memberRepo.findByMemberMail(memberMail).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(memberEntity);

        return memberResponseDto.getMemberIdx();
    }

    public Long loadMemberIdx(String memberMail) {

        Member memberEntity = memberRepo.findByMemberMail(memberMail).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(memberEntity);

        return memberResponseDto.getMemberIdx();
    }

    @Transactional
    public void deleteMember(Long idx) {

        memberRepo.deleteById(idx);

    }

    @Transactional
    public void limitMember(Long idx) {

        memberRepo.limitById(idx);
    }

    @Transactional
    public void unLimitMember(Long idx) {

        memberRepo.unLimitById(idx);
    }

    public MemberResponseDto loadMemberInfo(PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        Long memberIdx = member.getMemberIdx();
        String memberName = member.getMemberName();
        String memberMail = member.getMemberMail();
        String introduction = member.getIntroduction();
        String profileImage = member.getProfileImage();
        String visible = member.getVisible();

        return new MemberResponseDto(memberIdx, memberName, memberMail, introduction, profileImage, visible);
    }

    @Transactional
    public void editMember(MemberRequestDto dto) {

        Member memberEntity = memberRepo.findById(dto.getMemberIdx()).orElseThrow(() -> new UsernameNotFoundException("인증되지 않았습니다."));

        // 더티체킹
        memberEntity.modifyInfo(dto);

    }


//    public MemberResponseDto signin(MemberRequestDto memberRequestDto) {
//    }
}
