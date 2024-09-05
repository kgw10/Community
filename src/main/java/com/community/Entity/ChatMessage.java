package com.community.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;      // 메시지를 보낸 사람
    private String message;     // 메시지 내용
    private LocalDateTime timestamp; // 메시지가 작성된 시간

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;  // 메시지가 속한 채팅방
}