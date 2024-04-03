package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private Long boardIdx;
    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;
    private Subject boardSubject;
    private Member boardWriter;
    private Member memberIdx;
    private int viewCount;
    private int likeCount;
    private String boardImage;
    private String boardContent;

    @Builder
    public BoardRequestDto(Long boardIdx, String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, Member boardWriter, Member memberIdx, int viewCount, int likeCount, String boardImage, String boardContent) {
        this.boardIdx = boardIdx;
        this.boardCategory1 = boardCategory1;
        this.boardCategory2 = boardCategory2;
        this.boardTitle = boardTitle;
        this.boardSubject = boardSubject;
        this.boardWriter = boardWriter;
        this.memberIdx = memberIdx;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.boardImage = boardImage;
        this.boardContent = boardContent;
    }

    public Board toEntity() {
        return Board.builder()
                .boardIdx(boardIdx)
                .boardCategory1(boardCategory1)
                .boardCategory2(boardCategory2)
                .boardTitle(boardTitle)
                .boardSubject(boardSubject)
                .member(boardWriter)
                .viewCount(viewCount)
                .likeCount(likeCount)
                .boardImage(boardImage)
                .boardContent(boardContent)
                .build();
    }



}
