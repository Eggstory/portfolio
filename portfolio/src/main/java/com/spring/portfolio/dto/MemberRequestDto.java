package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Member;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String memberAddress;
    private String memberPost;
    private String memberPhone;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String memberLock;
    private String memberRole;


    @Builder
    public MemberRequestDto(String memberId, String memberPw, String memberName, String memberMail, String memberAddress, String memberPost, String memberPhone, String memberLock, String memberRole) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.memberAddress = memberAddress;
        this.memberPost = memberPost;
        this.memberPhone = memberPhone;
        this.memberLock = "ACTIVE";
        this.memberRole = "ROLE_USER";
    }


    // to Entity방식은 return builder.build() 방식이 맞는듯
    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .memberPw(memberPw)
                .memberName(memberName)
                .memberMail(memberMail)
                .memberAddress(memberAddress)
                .memberPost(memberPost)
                .memberPhone(memberPhone)
                .memberLock(memberLock)
                .memberRole(memberRole)
                .build();
    }



}
