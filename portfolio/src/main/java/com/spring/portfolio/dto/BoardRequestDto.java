package com.spring.portfolio.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;
    private Subject boardSubject;
    private String boardWriter;
    private int viewCount;
    private int likeCount;
    private String boardImage;
    private String boardContent;

    @Builder
    public BoardRequestDto(String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, String boardWriter, int viewCount, int likeCount, String boardImage, String boardContent) {
        this.boardCategory1 = boardCategory1;
        this.boardCategory2 = boardCategory2;
        this.boardTitle = boardTitle;
        this.boardSubject = boardSubject;
        this.boardWriter = boardWriter;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.boardImage = boardImage;
        this.boardContent = boardContent;
    }



}
