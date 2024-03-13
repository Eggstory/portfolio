package com.spring.portfolio.config;

import com.spring.portfolio.dto.Subject;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubjectConverter implements AttributeConverter<Subject, String> {


    @Override
    public String convertToDatabaseColumn(Subject subject) {
        return Optional.ofNullable(subject).orElse(Subject.NORMAL).getValue();
    }

    @Override
    public Subject convertToEntityAttribute(String dbData) {

        return Subject.convert(dbData);
    }
}
