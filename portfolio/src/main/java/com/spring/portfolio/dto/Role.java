package com.spring.portfolio.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    MASTER("MASTER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

}
