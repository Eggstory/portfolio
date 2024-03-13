package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Board;
import com.spring.portfolio.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private Long idx;
    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;
    private String boardSubject;
    private String boardWriter;
    private int viewCount;
    private int likeCount;
    private String boardImage;
    private String boardContent;
    private Timestamp createDate;
    private Timestamp updateDate;

    @Builder
    public BoardResponseDto(Board board) {
        this.idx = board.getIdx();
        this.boardCategory1 = board.getBoardCategory1();
        this.boardCategory2 = board.getBoardCategory2();
        this.boardTitle = board.getBoardTitle();
        this.boardSubject = board.getBoardSubject().getValue();
        this.boardWriter = board.getBoardWriter();
        this.viewCount = board.getViewCount();
        this.likeCount = board.getLikeCount();
        this.boardImage = board.getBoardImage();
        this.boardContent = board.getBoardContent();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
    }

}
