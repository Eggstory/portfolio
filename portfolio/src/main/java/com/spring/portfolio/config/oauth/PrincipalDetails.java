package com.spring.portfolio.config.oauth;

import com.spring.portfolio.dto.Role;
import com.spring.portfolio.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


// 시큐리티가 "/login" 주소 요청이 오면 낚아 채서 로그인을 진행해준다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다.(Security Session(Session안에 특정영역))
// 해당 세션안에는 Authentication 타입객체가 들어간다.
// Authentication 은 UserDetails 타입 객체가 들어갈수 있다.
// UserDetails 안에 use(사용자)를 가지고 있는다.
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;
    String attributeKey;

    // UserDetails : Form 로그인 시 사용
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // OAuth2User : OAuth2 로그인 시 사용
    public PrincipalDetails(Member member, Map<String, Object> attributes, String userNameAttributeName) {
        //Oauth2UserService 참고
        this.member = member;
        this.attributes = attributes;
        this.attributeKey = userNameAttributeName;
    }

    // 요거는 어떻게 만들어진거지??
    // 이거 만들어주는 이유가 다중구현을 위해
    // OAuth2User(소셜로그인)와 UserDetails(일반로그인)의 타입을 해결하기 위해 PrincipalDetails를 다중 구현한다.
    // 그러면 소셜로그인과 일반로그인의 타입이 같아진다.
    @Override
    public Map<String, Object> getAttributes() {
        // 소셜로부터 받은 사용자 정보를 Map<String, Object> 타입으로 리턴
        return attributes;
    }

    // 해당 User의 권한을 리턴하는 곳!(role)
    // SecurityFilterChain에서 권한을 체크할 때 사용됨
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Role role = member.getMemberRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;

        // 위 코드랑 아래 코드랑 같은거 같은데 흐음
//        return Collections.singletonList(new SimpleGrantedAuthority(member.getMemberRole()));


//        // 다른방법
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getMemberRole().getAuthority();
//            }
//        });
//    return  collection;

    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    // AbstractAuthenticationToken에서 getName()호출 시 -> principal이 UserDetails이면 userDetails.getUsername()을 리턴
    @Override
    public String getUsername() {
//        return member.getMemberMail();
        return member.getMemberKey();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //우리사이트!! 1년 동안 사용하지 않으면 휴면 계정으로 바꾼다.
        // 현재 시간 - 마지막 로그인 시간 => 1년을 초기하면 return false로 바꾼다.
        // 이러한 로직을 여기 넣는다.

        return true;
    }

    // authentication.getName() -> Principal 객체의 getName()을 호출
    // TokenProvider에서 jwts.subject에 들어갈 값
    @Override
    public String getName() {
        // 소셜로그인한 사용자의 성명을 return한다.
//        String sub = attributes.get("sub").toString();
//        return sub;

//        return (String) attributes.get("name"); // name키에 저장된 값은 google에서 해당 유저의 성명에 해당함(given_name은 이름, family_name은 성)


        // userNameAttributeName
        // attributes(유저정보)
        return attributes.get(attributeKey).toString();
    }
}
