package com.spring.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.portfolio.dto.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String memberAddress;
    private String memberPost;
    private String memberPhone;
    private String memberLock;

    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Role memberRole;

    private int failedAttempt;
    private LocalDateTime lockTime;

//    @Embedded
//    private Address address;

    @Builder
    public Member(String memberId, String memberPw, String memberName, String memberMail, String memberAddress, String memberPost, String memberPhone, String memberLock, Role memberRole, int failedAttempt) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.memberAddress = memberAddress;
        this.memberPost = memberPost;
        this.memberPhone = memberPhone;
        this.memberLock = memberLock;
        this.memberRole = memberRole;
        this.failedAttempt = failedAttempt;
    }


    public void updateLock(String locked, LocalDateTime date) {
        this.memberLock = locked;
        this.lockTime = date;
    }


    public void addFailedAttempt(boolean b) {
        if(b) {
            this.failedAttempt = failedAttempt + 1;
        } else {
            this.failedAttempt = 0;
        }
    }

//    public void setMemberPw(String encode) {
//        this.memberPw = encode;
//    }

}
