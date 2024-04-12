package com.spring.portfolio.service;


import com.spring.portfolio.entity.Token;
import org.springframework.stereotype.Service;

@Service
public class TokenService {


    public void saveOrUpdate(String name, String refreshToken, String accessToken) {

    }

    public Token findByAccessTokenOrThrow(String accessToken) {


    }

    public void updateToken(String reissueAccessToken, Token token) {

    }
}
