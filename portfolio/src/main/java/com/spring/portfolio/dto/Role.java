package com.spring.portfolio.dto;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    LIMIT("LIMIT"),
    ADMIN("ADMIN"),
    MASTER("MASTER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

}
