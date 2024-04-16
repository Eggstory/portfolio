package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Address;
import com.spring.portfolio.entity.Member;
import lombok.*;

// 일반 로그인(세션) dto
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

//    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private Role memberRole;


    @Builder
    public MemberRequestDto(String memberPw, String memberName, String memberMail, String profile, Address memberAddress, String detailAddress, String memberPhone) {
//        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.memberRole = Role.USER;
    }


    // to Entity방식은 return builder.build() 방식이 맞는듯
    public Member toEntity() {
        return Member.builder()
//                .memberId(memberId)
                .memberPw(memberPw)
                .memberName(memberName)
                .memberMail(memberMail)
//                .profile(profile)
//                .address(memberAddress)
//                .detailAddress(detailAddress)
//                .memberPhone(memberPhone)
//                .memberLock(memberLock)
                .memberRole(memberRole)
//                .failedAttempt(failedAttempt)
                .build();
    }



}
