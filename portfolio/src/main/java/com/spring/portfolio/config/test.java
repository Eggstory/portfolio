package com.spring.portfolio.config;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class test extends UsernamePasswordAuthenticationToken {
    public test(Object principal, Object credentials) {
        super(principal, credentials);
    }
}