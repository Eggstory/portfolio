package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    private Long memberIdx;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String memberAddress;
    private String detailAddress;
    private String zonecode;
    private String memberPhone;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String memberLock;
    private String memberRole;
    private boolean isLimited;
    private String memberStatus;
    private String introduction;
    private String profileImage;
    private String visible;

//    private int failedAttempt;
//    private LocalDateTime lockTime;

    @Builder
    public MemberResponseDto(Long memberIdx, String memberName, String memberMail, String introduction, String profileImage, String visible) {
        this.memberIdx = memberIdx;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.introduction = introduction;
        this.profileImage = profileImage;
        this.visible = visible;
    }

    @Builder
    public MemberResponseDto(Member member) {
        this.memberIdx = member.getMemberIdx();
        this.memberId = member.getMemberMail().split("@")[0];
        this.memberPw = member.getMemberPw();
        this.memberName = member.getMemberName();
//        if(!member.isLimited()) {
//            this.memberName = member.getMemberName();
//        } else {
//            this.memberName = "[정지된 계정]";
//        }
        this.memberMail = member.getMemberMail();
        this.createDate = member.getCreateDate();
//        this.memberAddress = member.getAddress().getCity() + " " +
//                member.getAddress().getStreet() + " " + member.getAddress().getZipcode();
//        this.zonecode = member.getAddress().getZonecode();
//        this.detailAddress = member.getDetailAddress();
//        this.memberPhone = member.getMemberPhone();
//        this.memberLock = member.getMemberLock();
        this.memberRole = member.getMemberRole().getAuthority();
        this.isLimited = member.isLimited();
        if(!isLimited) {
            this.memberStatus = "활동";
        } else {
            this.memberStatus = "정지";
        }
        this.visible = member.getVisible();
//        this.failedAttempt = member.getFailedAttempt();
//        this.lockTime = member.getLockTime();
    }


}
