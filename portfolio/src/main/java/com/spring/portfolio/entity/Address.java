package com.spring.portfolio.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    // 지역 (경기도 성남시 분당구)
    private String city;
    // 도로명 (판교역로)
    private String street;
    // 건물번호 (166)
    private String zipcode;
    // 우편번호
    private String zonecode;

    public Address(String city, String street, String zipcode, String zonecode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.zonecode = zonecode;
    }

}
