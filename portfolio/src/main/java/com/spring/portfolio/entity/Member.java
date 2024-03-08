package com.spring.portfolio.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberMail;
    private String memberAddress;
    private String memberPost;
    private String memberPhone;
    private String memberLock;
    private String memberRole;

    @Builder
    public Member(String memberId, String memberPw, String memberName, String memberMail, String memberAddress, String memberPost, String memberPhone, String memberLock, String memberRole) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.memberAddress = memberAddress;
        this.memberPost = memberPost;
        this.memberPhone = memberPhone;
        this.memberLock = memberLock;
        this.memberRole = memberRole;
    }

}
