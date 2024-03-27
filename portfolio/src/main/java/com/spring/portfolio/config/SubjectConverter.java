package com.spring.portfolio.config;

import com.spring.portfolio.dto.Subject;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubjectConverter implements AttributeConverter<Subject, String> {


    // 테이블에 저장혹은 변경될때 발동
    @Override
    public String convertToDatabaseColumn(Subject subject) {
        return Optional.ofNullable(subject).orElse(Subject.NORMAL).getValue();
    }

    // 테이블에서 출력이 될때 발동
    @Override
    public Subject convertToEntityAttribute(String dbData) {

        return Subject.convert(dbData);
    }
}
