package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Member;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

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
    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.memberPw = member.getMemberPw();
        this.memberName = member.getMemberName();
        this.memberMail = member.getMemberMail();
        this.memberAddress = member.getMemberAddress();
        this.memberPost = member.getMemberPost();
        this.memberPhone = member.getMemberPhone();
        this.memberLock = member.getMemberLock();
        this.memberRole = member.getMemberRole();
    }

}
