package com.spring.portfolio.config.oauth;

import com.spring.portfolio.entity.Member;
import com.spring.portfolio.store.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth 서비스(github, google, naver, kakao)에서 가져온 유저 정보를 담고있음
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 서비스의 유저 정보들
        // oAuth2User.getAttributes() : loadUser로 받아온 oAuth2User 객체의 각 요소들을 가져옴
        // sub, name, given_name, family_name, picture, email, email_verified, locale, hd
        // {sub=101301106118139334837, name=고경환, given_name=경환, family_name=고, picture=https://lh3.googleusercontent.com/a-/AFdZucqfqgcr-H-cRolGyJETVNk, email=gkw1207@likelion.org, email_verified=true, locale=en, hd=likelion.org}
        // **회원가입할때 저장될 정보** => username: "google_101301106118139334837", password: "암호화(get in there)", email: "gkw1207@likelion.org", role: "ROLE_USER"
        // 1. 유저 정보(attributes) 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // OAuth 서비스 이름(ex. github, naver, google, kakao) (yml에서 client.registration값)
        // 2. resistrationId 가져오기 (third-party id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth 로그인 시 키 값. 구글, 네이버, 카카오 등 각 다르기 때문에 변수로 받아서 넣음 (yml에서 user-name-attribute값)
        // CommonOAuth2Provider에서 값 확인 가능 (나는 안 만든듯)
        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // @AuthenticationPrincipal이라는 어노테이션을 통해서 세션정보를 받을 수 있다.(원래는 UserDetails userDetails 인데 UserDetails를 구현한 PrincipalDetails로 타입으로 지정해도 무방 -> .getUser()사용하기 위함)
        // Authentication authentication : (PrincipalDetails) authentication.getPrincipal.getMember()
        // @AuthenticationPrincipal PrincipalDetails userDetails : userDetails.getMember()
        // 위 두 값은 동일 Member(id=1, username=ko1)

        // Authentication authentication : authentication.getPrinciapl()
        // OAuth2User oauth : oauth.getAttributes()
        // 위 두 값은 동일

        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌(Dto)
        // OAuth2UserInfo.of 이랑 같은 역할 하는듯 OAuthAttributes.extract
//        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);

        // 4. 유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, attributes);

        // 5. 회원가입 및 로그인
        Member member = getOrSaveUserProfile(oAuth2UserInfo);

        // userNameAttributeName : OAuth2 로그인 시 키(PK)가 되는 값
        // attributes : OAuth 서비스의 유저 정보들
//        return createDefaultOAuth2User(member, attributes, userNameAttributeName);

        // 6. OAuth2User로 반환
        return new PrincipalDetails(member, attributes, userNameAttributeName);
    }

    private Member getOrSaveUserProfile(OAuth2UserInfo oAuth2UserInfo) {

        Member member = memberRepository.findByMemberMail(oAuth2UserInfo.getEmail())
                .orElseGet(oAuth2UserInfo::toEntity);
        return memberRepository.save(member);
//
//        if( member != null) {
//            // sub, name, picture
//            member = member.update(userProfile.getUserId(), userProfile.getNickname(),
//                    userProfile.getProfileImageUrl());
//        } else {
//            member = memberRepository.save(userProfile.toEntity());
//        }
//        return member;
    }

    private DefaultOAuth2User createDefaultOAuth2User(Member member, Map<String, Object> attributes,
                                                      String userNameAttributeName) {
        // DefaultOAuth2User는 OAuth2User의 구현체다
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(member.getMemberRole().getAuthority())),
                attributes,
                userNameAttributeName
        );
    }

}
