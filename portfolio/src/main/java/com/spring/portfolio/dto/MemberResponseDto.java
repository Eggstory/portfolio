package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String memberAddress;
    private String memberPost;
    private String memberPhone;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String memberLock;
    private Role memberRole;
    private int failedAttempt;
    private LocalDateTime lockTime;

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
        this.failedAttempt = member.getFailedAttempt();
        this.lockTime = member.getLockTime();
    }

}
