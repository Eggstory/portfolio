package com.spring.portfolio.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    public void setSender(Member sender) {
        this.sender = sender;
    }

    public void setReceiver(Member receiver) {
        this.receiver = receiver;
    }

    @Builder
    protected ChatRoom(Long roomId, Member sender, Member receiver) {
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
