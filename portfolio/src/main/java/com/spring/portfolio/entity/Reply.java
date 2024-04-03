package com.spring.portfolio.entity;

import com.spring.portfolio.dto.BoardRequestDto;
import com.spring.portfolio.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyIdx;

    @Column(length = 500)
    private String replyComment;

//    private int group;
//
//    private int num;

    //해당 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    //댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    //부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_idx")
    private Reply parent;

    //자식 댓글들
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
//    @JoinColumn(name = "replies_idx")
    @Builder.Default
    private List<Reply> Replies = new ArrayList<>();

    @Builder
    public Reply(Long replyIdx, String replyComment, Board board, Member member, Reply parent) {
        this.replyIdx = replyIdx;
        this.replyComment = replyComment;
        this.board = board;
        this.member = member;
        this.parent = parent;
    }

    public void modifyReply(ReplyRequestDto dto) {

        this.replyIdx = dto.getReplyIdx();
        this.replyComment = dto.getReplyComment();
        this.board = dto.getBoardIdx();
        this.member = dto.getMemberIdx();
        this.parent = dto.getParentIdx();

    }
}
