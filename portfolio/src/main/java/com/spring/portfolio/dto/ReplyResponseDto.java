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
    private String mail;
    private String memberId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private Long parent;
    private List<ReplyResponseDto> replyList = new ArrayList<>();


    public ReplyResponseDto(Reply reply) {
        this.idx = reply.getReplyIdx();
        this.replyComment = reply.getReplyComment();
        this.writer = reply.getMember().getMemberName();
        this.mail = reply.getMember().getMemberMail();
        this.memberId = mail.split("@")[0];
        this.createDate = reply.getCreateDate();
        this.updateDate = reply.getUpdateDate();
        if(reply.getParent() == null) {
            this.parent = 0L;
        } else {
            this.parent = reply.getParent().getReplyIdx();
        }

        if(reply.getReplies() != null) {
            this.replyList = reply.getReplies().stream().map(ReplyResponseDto::new).collect(Collectors.toList());
        }
    }
}
