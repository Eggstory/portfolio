package com.spring.portfolio.dto;

import com.spring.portfolio.entity.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResponseDto {

    private Long idx;
    private String replyComment;
    private String writer;
    private Long boardIdx;
    private String boardTitle;
    private String mail;
    private String memberId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isDeleted;
    private boolean change;
    private String blindYN;

    private Long parent;
    private List<ReplyResponseDto> replyList = new ArrayList<>();


    public ReplyResponseDto(Reply reply) {
        this.idx = reply.getReplyIdx();
        if (reply.getMember() == null && reply.getReplyComment().isEmpty()) {
            this.writer = "[삭제된 댓글]";
            this.mail = "";
            this.memberId = "";
            this.replyComment = "관리자에 의해 삭제되었습니다.";
            this.change = false;
        } else if (reply.getMember() == null) {
            this.writer = "[탈퇴한 계정]";
            this.mail = "";
            this.memberId = "";
            this.replyComment = reply.getReplyComment();
            this.change = true;
        } else if (reply.getMember().isLimited()) {
            this.writer = "[정지된 계정]";
            this.mail = "";
            this.memberId = "";
            this.replyComment = "삭제된 댓글입니다.";
            this.change = false;
        } else if (reply.isDeleted()) {
            this.writer = reply.getMember().getMemberName();
            this.mail = reply.getMember().getMemberMail();
            this.memberId = "(" + mail.split("@")[0] + ")";
            this.replyComment = "삭제된 댓글입니다.";
            this.change = false;
        } else {
            this.writer = reply.getMember().getMemberName();
            this.mail = reply.getMember().getMemberMail();
            this.memberId = "(" + mail.split("@")[0] + ")";
            this.replyComment = reply.getReplyComment();
            this.change = true;
        }
        this.boardIdx = reply.getBoard().getBoardIdx();
        this.boardTitle = reply.getBoard().getBoardTitle();
        this.createDate = reply.getCreateDate();
        this.updateDate = reply.getUpdateDate();
        this.isDeleted = reply.isDeleted();
        if (isDeleted) {
            this.blindYN = "Y";
        } else {
            this.blindYN = "N";
        }
        if (reply.getParent() == null) {
            this.parent = 0L;
        } else {
            this.parent = reply.getParent().getReplyIdx();
        }

        if (reply.getReplies() != null) {
            this.replyList = reply.getReplies().stream().map(ReplyResponseDto::new).collect(Collectors.toList());
        }
    }
}
