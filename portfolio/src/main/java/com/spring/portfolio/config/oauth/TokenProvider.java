package com.spring.portfolio.config.oauth;

import com.spring.portfolio.entity.Token;
import com.spring.portfolio.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.portfolio.dto.ErrorCode.INVALID_JWT_SIGNATURE;
import static com.spring.portfolio.dto.ErrorCode.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class TokenProvider {


    @Value("${jwt.secret}")
    private String key;
    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String KEY_ROLE = "role";
    private final TokenService tokenService;

    // @PostConstruct는 의존성 주입이 이루어진 후 초기화를 수행하는 메서드
    // 객체 초기화, secretKey 인코딩
    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    // Access token 발급
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // refresh token 발급
    public void generateRefreshToken(Authentication authentication, String accessToken) {
        String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
        tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken);
    }

    // 1. 토큰 발급
    private String generateToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        // sub(토큰제목), iss(토큰발급자), iat(토큰 발급 시간), exp(토큰 만료 시간), roles(권한)
        // claim은 Registered claim, Public claim, Private claim으로 나눠진다.
        // Jwt.builder에서 헤더의 타입(typ), 발급 시간(issuedAt), 만료 시간(expiration), 비공개 클레임(signWith) 설정가능
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    // token은 jwt의 비밀번호로 들어가는듯
    public Authentication getAuthentication(String token) {
        // 토큰 복호화 ( header : alg, typ  /  payload : sub, name, email 이런것들  /  signature : header + payload + secretKey
        Claims claims = parseClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        // 2. security의 User 객체 생성 ( username(?), token, 권한 )
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(
                claims.get(KEY_ROLE).toString()));
    }

    // 3. accessToken 재발급
    public String reissueAccessToken(String accessToken) {
        if (StringUtils.hasText(accessToken)) {
            Token token = tokenService.findByAccessTokenOrThrow(accessToken);
            String refreshToken = token.getRefreshToken();

            if (validateToken(refreshToken)) {
                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
                tokenService.updateToken(reissueAccessToken, token);
                return reissueAccessToken;
            }
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) {
            return false;
        }

        // 복호화
        Claims claims = parseClaims(token);
        return claims.getExpiration().after(new Date());
    }

    // 복호화
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (MalformedJwtException e) {
            throw new TokenException(INVALID_TOKEN, "유효한 토큰이 없습니다.");
        } catch (SecurityException e) {
            throw new TokenException(INVALID_JWT_SIGNATURE,"JWT 서명이 잘못되었습니다.");
        }
    }



}
