package com.spring.portfolio.config;

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
    String userNameAttributeName;

    // UserDetails : Form 로그인 시 사용
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // OAuth2User : OAuth2 로그인 시 사용
    public PrincipalDetails(Member member, Map<String, Object> attributes, String userNameAttributeName) {
        this.member = member;
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Role role = member.getMemberRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    @Override
    public String getUsername() {
        return member.getMemberMail();
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

        return true;
    }

    @Override
    public String getName() {

        return attributes.get(userNameAttributeName).toString();
    }
}
