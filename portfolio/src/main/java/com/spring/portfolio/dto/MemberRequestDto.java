package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Address;
import com.spring.portfolio.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String profile;
    private Address memberAddress;
    private String detailAddress;
    private String memberPhone;
    private String memberLock;
    private Role memberRole;
//    private int failedAttempt;
//    private LocalDateTime lockTime;


    @Builder
    public MemberRequestDto(String memberId, String memberPw, String memberName, String memberMail, String profile, Address memberAddress, String detailAddress, String memberPhone) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.profile = profile;
        this.memberAddress = memberAddress;
        this.detailAddress = detailAddress;
        this.memberPhone = memberPhone;
        this.memberLock = "ACTIVE";
        this.memberRole = Role.USER;
//        this.failedAttempt = 0;
//        this.lockTime = null;
    }


    // to Entity방식은 return builder.build() 방식이 맞는듯
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .memberPw(memberPw)
                .memberName(memberName)
                .memberMail(memberMail)
                .profile(profile)
                .address(memberAddress)
                .detailAddress(detailAddress)
                .memberPhone(memberPhone)
                .memberLock(memberLock)
                .memberRole(memberRole)
//                .failedAttempt(failedAttempt)
                .build();
    }



}
