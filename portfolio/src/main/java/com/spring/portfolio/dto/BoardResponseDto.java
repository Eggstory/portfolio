package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private Long boardIdx;
    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;
    private String boardSubject;
    private String boardWriter;
    private Long memberIdx;
    private int viewCount;
    private int likeCount;
    private String boardImage;
    private String boardContent;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public BoardResponseDto(Board board) {
        this.boardIdx = board.getBoardIdx();
        this.boardCategory1 = board.getBoardCategory1();
        this.boardCategory2 = board.getBoardCategory2();
        this.boardTitle = board.getBoardTitle();
        this.boardSubject = board.getBoardSubject().getValue();
        this.boardWriter = board.getMember().getMemberName();
        this.memberIdx = board.getMember().getMemberIdx();
        this.viewCount = board.getViewCount();
        this.likeCount = board.getLikeCount();
        this.boardImage = board.getBoardImage();
        this.boardContent = board.getBoardContent();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
    }

    public void setBoardImage(String s) {
        this.boardImage = s;
    }
}
