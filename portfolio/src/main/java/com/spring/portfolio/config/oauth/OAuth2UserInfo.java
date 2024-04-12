package com.spring.portfolio.config.oauth;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.crypto.KeyGenerator;
import java.util.Map;

@Builder
@Getter
public class OAuth2UserInfo {

    private String name; // 이름 정보
    private String email; // 이메일 정보
    private String profile; // 프로필 사진 정보

    //서비스에 따라 OAuth2Attribute 객체를 생성하는 메서드
    static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            case "naver" -> ofNaver(attributes);
//            default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
            default -> throw new RuntimeException();
        };
    }
    /*
     *   Google 로그인일 경우 사용하는 메서드, 사용자 정보가 따로 Wrapping 되지 않고 제공되어,
     *   바로 get() 메서드로 접근이 가능하다.
     * */
    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo .builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }
    /*
     *   Kakao 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 kakaoAccount -> kakaoProfile 두번 감싸져 있어서,
     *   두번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }
    /*
     *  Naver 로그인일 경우 사용하는 메서드, 필요한 사용자 정보가 response Map에 감싸져 있어서,
     *  한번 get() 메서드를 이용해 사용자 정보를 담고있는 Map을 꺼내야한다.
     * */
    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2UserInfo.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .profile((String) response.get("profile_image"))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .memberName(name)
                .memberMail(email)
                .profile(profile)
                .memberRole(Role.USER)
                .build();
    }


}
