package com.spring.portfolio.config.oauth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

// 여러 OAuth 공급자(ex. google, kakao, naver 등)의 속성을 처리하는 역할을 한다.
//서비스에 따라 얻어온 유저 정보의 key 값이 다르기 때문에 각각 관리해주어야 한다.

// 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)
// OAuth2UserInfo oauth2UserInfo;

public enum OAuthAttributes {

    // String.valueOf : null 이 들어올 시, 문자열 null 이라 나옴
    // toString : null 이 들어올 시, NPE 발생
    GOOGLE("google", attributes -> new UserProfile(
            String.valueOf(attributes.get("sub")),
            (String) attributes.get("name"),
            (String) attributes.get("email"),
            (String) attributes.get("picture")
    )),

    NAVER("naver", attributes -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new UserProfile(
                (String) response.get("id"),
                (String) response.get("name"),
                (String) response.get("email"),
                (String) response.get("profile_image")
        );
    }),

    KAKAO("kakao", attributes -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return new UserProfile(
                (String) kakaoAccount.get("id"),
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                (String) profile.get("profile_image_url")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, UserProfile> userProfileFactory;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> userProfileFactory) {
        this.registrationId = registrationId;
        this.userProfileFactory = userProfileFactory;
    }

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                // Function<T,R>
                // R apply(T t)
                .userProfileFactory.apply(attributes);  // attributes 객체를 객체 UserProfile로 매핑
    }
}
