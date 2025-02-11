package com.spring.portfolio.entity;

import com.spring.portfolio.config.SubjectConverter;
import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
//@DynamicUpdate // Entity update시, 원하는 데이터만 update하기 위함
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    @NotNull
    private String boardCategory1;
    @NotNull
    private String boardCategory2;
    @NotBlank
    private String boardTitle;

    @Convert(converter = SubjectConverter.class)
    @NotNull
    private Subject boardSubject;
    private int viewCount;
    private int likeCount;
    @Column(length = 1000)
    private String boardImage;
    //    @Column(columnDefinition = "TEXT", nullable = false)  //  columnDefinition : db 컬럼 정보를 여기다 줄 수 있음
    @Column(length = 1000)
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //cascade = CascadeType.REMOVE
//CascadeType.ALL + orphanRemoval=true
//이 두개를 같이 사용하게 되면 부모 엔티티가 자식의 생명주기를 모두 관리할 수 있게 된다.
//    @JoinColumn(name = "replies_idx")
    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    @OrderBy("idx asc")  // 댓글 정렬
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();


    @Builder
    protected Board(Long boardIdx, String boardCategory1, String boardCategory2, String boardTitle, Subject boardSubject, int viewCount, int likeCount, String boardImage, String boardContent, Member member, List<Reply> replies) {
        this.boardIdx = boardIdx;
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

    // JPA 더티체킹으로 대체
//    public void modifyBoard(BoardRequestDto dto) {
//
//        this.boardIdx = dto.getBoardIdx();
//        this.boardCategory1 = dto.getBoardCategory1();
//        this.boardCategory2 = dto.getBoardCategory2();
//        this.boardTitle = dto.getBoardTitle();
//        this.boardSubject = dto.getBoardSubject();
//        this.viewCount = dto.getViewCount();
//        this.likeCount = dto.getLikeCount();
//        this.boardImage = dto.getBoardImage();
//        this.boardContent = dto.getBoardContent();
//        this.member = dto.getMemberIdx();
//        this.replies = dto.toEntity().getReplies();
//
//    }

}
