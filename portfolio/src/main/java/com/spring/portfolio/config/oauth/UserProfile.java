package com.spring.portfolio.config.oauth;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.crypto.KeyGenerator;

@Getter
public class UserProfile {

    private final String oauthId;
    private final String name;
    private final String email;
    private final String profileImageUrl;

    @Builder
    public UserProfile(String oauthId, String name, String email, String profileImageUrl) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }


//    public Member toEntity() {
//        return new Member(oauthId, nickname, userId, profileImageUrl);
//    }

public Member toEntity() {
    return Member.builder()
            .providerId(oauthId)
            .memberMail(email)
            .memberName(name)
            .profile(profileImageUrl)
            .memberRole(Role.USER)
//            .memberKey(KeyGenerator.generateKey())
            .build();
}

}
