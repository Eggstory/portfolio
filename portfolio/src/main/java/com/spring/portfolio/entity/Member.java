package com.spring.portfolio.entity;

import com.spring.portfolio.dto.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@SuperBuilder   // 자식 객체가 부모객체의 필드를 builder 패턴으로 사용가능하게 해줌
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

//    private String memberId;
    private String memberPw;
    private String memberName;
    @Column(unique = true)
    private String memberMail;
    private String profileImage;
    private String introduction;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isLimited;

//    @Embedded
//    private Address address;
//    private String detailAddress;
//    private String memberPhone;
//    private String memberLock;

    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Role memberRole;

//    private int failedAttempt;
//    private LocalDateTime lockTime;

    // 사용자가 로그인한 서비스(ex) google, naver..  -  null 이면 form 로그인?)
    private String provider;    // null, google, kakao, naver 중에 한개
    private String providerId;  // 각 사이트에서 사용자별로 부여된 고유 id // attributes에서 sub이름을 가진 값을 받아서 리턴(구글)
//    private String refreshToken;   //  jwt 토큰키 인거같은데(refresh Token)

    @Builder
    public Member(String memberPw, String memberName, String memberMail, String profileImage, String introduction, boolean isLimited, Role memberRole, String provider, String providerId) {
//        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.isLimited = isLimited;
        this.memberRole = memberRole;
        this.provider = provider;
        this.providerId = providerId;
//        this.refreshToken = refreshToken;
    }

//    // , Address address, String detailAddress, String memberPhone, String memberLock
//    @Builder
//    public Member(String memberId, String memberPw, String memberName, String memberMail, String profileImage, Role memberRole,String provider, String providerId, String memberKey) {
//        this.memberId = memberId;
//        this.memberPw = memberPw;
//        this.memberName = memberName;
//        this.memberMail = memberMail;
////        this.address = address;
////        this.detailAddress = detailAddress;
////        this.memberPhone = memberPhone;
////        this.memberLock = memberLock;
//        this.memberRole = memberRole;
//        this.provider = provider;
//        this.providerId = providerId;
//        this.memberKey = memberKey;
////        this.failedAttempt = failedAttempt;
//    }

//    public void updateLock(String locked, LocalDateTime date) {
//        this.memberLock = locked;
//        this.lockTime = date;
//    }
//
//
//    public void addFailedAttempt(boolean b) {
//        if(b) {
//            this.failedAttempt = failedAttempt + 1;
//        } else {
//            this.failedAttempt = 0;
//        }
//    }

//    @Builder
//    public Member update(String providerId, String username, String imageUrl) {
//        this.providerId = providerId;
//
//    }


//  참고용으로 가져와봄
//    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
//    public User(String username, String password, String email, Role role) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//    }
//
//    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
//    public User(String username, String password, String email, Role role, String provider, String providerId) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.provider = provider;
//        this.providerId = providerId;
//    }
    
    
}
