package com.spring.portfolio.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRequestDto {

    private String replyComment;
    private Long boardIdx;
    private Long memberId;
    private Long parentId;

    @Builder
    public ReplyRequestDto(String replyComment) {
        this.replyComment = replyComment;
    }
}
