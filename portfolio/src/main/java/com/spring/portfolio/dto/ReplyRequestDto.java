package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import com.spring.portfolio.entity.Reply;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRequestDto {

    private Long replyIdx;
    private String replyComment;
    private Board boardIdx;
    private Member memberIdx;
    private Reply parentIdx;

    @Builder
    public ReplyRequestDto(Long replyIdx, String replyComment, Board boardIdx, Member memberIdx, Reply parentIdx) {
        this.replyIdx = replyIdx;
        this.replyComment = replyComment;
        this.boardIdx = boardIdx;
        this.memberIdx = memberIdx;
        this.parentIdx = parentIdx;
    }

    public Reply toEntity() {
        return Reply.builder()
                .replyIdx(replyIdx)
                .replyComment(replyComment)
                .board(boardIdx)
                .member(memberIdx)
                .parent(parentIdx)
                .build();
    }

}
