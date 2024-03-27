package com.spring.portfolio.dto;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Subject {

    NORMAL("NORMAL","일반"),
    NOTICE("NOTICE","공지"),
    PICTURE("PICTURE","그림");

    private final String key;
    private final String value;

    Subject(String key, String value) {
        this.key = key;
        this.value = value;
    }
    // Collections.unmodifiableMap : 읽기 전용 (변경이 있을 시 예외발생)
    // stream : 배열생성 , of(값) : 배열의 값들을 배열에 넣음
    // .collect() : stream 을 사용할 경우 최종연산에 쓰임
    //  Collectors.toMap : Map 으로 변환시 사용 ( collect()의 파라미터에 쓰이는듯)
    // toMap() 안 괄호는 Map<타입, 타입> 에 들어갈 것들
    // Map<타입, 타입> 이런식이면 toMap(인자, 인자)가 들어가지만 -> Stream<Entity> 일때 toMap(Entity::key, Entity::value)
    // Map<타입, 클래스> 이런식이면 toMap(인자, Function.identity()) 이런식으로 쓰는듯
    // Function.identity() : 값이 실제 요소여야 하는 경우 사용한다함.
    private static final Map<String, Subject> descriptions = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(Subject::getValue, Function.identity())));

    public static Subject convert(String dbData) {
        return Optional.ofNullable(descriptions.get(dbData)).orElse(NORMAL);
    }

}
