package com.spring.portfolio.entity;

import com.spring.portfolio.dto.MemberRequestDto;
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

    private String memberPw;
    private String memberName;
    @Column(unique = true)
    private String memberMail;
    private String profileImage;
    private String introduction;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isLimited;
    @ColumnDefault("N")
    private String visible;

    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Role memberRole;

    // 사용자가 로그인한 서비스(ex) google, naver..  -  null 이면 form 로그인?)
    private String provider;    // null, google, kakao, naver 중에 한개
    private String providerId;  // 각 사이트에서 사용자별로 부여된 고유 id // attributes에서 sub이름을 가진 값을 받아서 리턴(구글)
//    private String refreshToken;   //  jwt 토큰키 (refresh Token)

    @Builder
    public Member(String memberPw, String memberName, String memberMail, String profileImage, String introduction, boolean isLimited, Role memberRole, String provider, String providerId, String visible) {
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberMail = memberMail;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.isLimited = isLimited;
        this.memberRole = memberRole;
        this.provider = provider;
        this.providerId = providerId;
        this.visible = visible;
//        this.refreshToken = refreshToken;
    }

    public void modifyInfo(MemberRequestDto dto) {

        this.visible = dto.getVisible();
        this.memberName = dto.getMemberName();
        this.introduction = dto.getIntroduction();
    }

    public void modifyPw(String memberPw) {

        this.memberPw = memberPw;
    }
}
