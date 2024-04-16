package com.spring.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;
    
//    생성일 포맷 바꾸는 코드였는데 작동안되서 주석
//    @PrePersist
//    public void onPerPersist() {
//        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        this.createDate = parsedCreateDate;
//    }
}
