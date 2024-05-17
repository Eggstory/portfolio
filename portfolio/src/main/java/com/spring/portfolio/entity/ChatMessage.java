package com.spring.portfolio.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public void setMember(Member sender) {
        this.sender = sender;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Builder
    public ChatMessage(Long messageId, String content, LocalDateTime sendTime, Member sender, ChatRoom chatRoom) {
        this.messageId = messageId;
        this.content = content;
        this.sendTime = sendTime;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }
}
