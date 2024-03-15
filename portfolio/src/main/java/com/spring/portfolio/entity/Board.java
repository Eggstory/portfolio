package com.spring.portfolio.entity;

import com.spring.portfolio.config.SubjectConverter;
import com.spring.portfolio.dto.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{

    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;

    @Convert(converter = SubjectConverter.class)
    private Subject boardSubject;
    private String boardWriter;
    private int viewCount;
    private int likeCount;
    private String boardImage;
    private String boardContent;

//    @OneToMany
//    @JoinColumn(name = "idx")
//    private Reply reply;

    @Builder
    public Board(String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, String boardWriter, int viewCount, int likeCount, String boardImage, String boardContent) {
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
