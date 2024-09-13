package com.community.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;     // 메시지 내용
    private LocalDateTime timestamp; // 메시지가 작성된 시간




    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;  // 메시지가 속한 채팅방
}