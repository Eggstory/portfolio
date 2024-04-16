package com.spring.portfolio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDto {

    private String provider;
    private String accessToken;
    private String refreshToken;

    public TokenDto(String provider, String accessToken, String refreshToken) {
        this.provider = provider;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
