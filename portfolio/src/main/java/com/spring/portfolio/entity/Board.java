package com.spring.portfolio.entity;

import com.spring.portfolio.config.SubjectConverter;
import com.spring.portfolio.dto.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    private String boardCategory1;
    private String boardCategory2;
    private String boardTitle;

    @Convert(converter = SubjectConverter.class)
    private Subject boardSubject;
    private int viewCount;
    private int likeCount;
    private String boardImage;
//    @Column(columnDefinition = "TEXT", nullable = false)  //  columnDefinition : db 컬럼 정보를 여기다 줄 수 있음
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

//CascadeType.ALL + orphanRemovel=true
//이 두개를 같이 사용하게 되면 부모 엔티티가 자식의 생명주기를 모두 관리할 수 있게 된다.
//    @JoinColumn(name = "replies_idx")
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("idx asc")  // 댓글 정렬
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();


    @Builder
    protected Board(String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, int viewCount, int likeCount, String boardImage, String boardContent, Member member, List<Reply> replies) {
        this.boardCategory1 = boardCategory1;
        this.boardCategory2 = boardCategory2;
        this.boardTitle = boardTitle;
        this.boardSubject = boardSubject;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.boardImage = boardImage;
        this.boardContent = boardContent;
        this.member = member;
        this.replies = replies;
    }



//    @Builder
//    protected Board(String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, String boardWriter, int viewCount, int likeCount, String boardImage, String boardContent) {
//        this.boardCategory1 = boardCategory1;
//        this.boardCategory2 = boardCategory2;
//        this.boardTitle = boardTitle;
//        this.boardSubject = boardSubject;
//        this.boardWriter = boardWriter;
//        this.viewCount = viewCount;
//        this.likeCount = likeCount;
//        this.boardImage = boardImage;
//        this.boardContent = boardContent;
//    }




}
