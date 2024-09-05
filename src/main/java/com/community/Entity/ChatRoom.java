package com.community.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 채팅방 ID

    private String title;     // 채팅방 제목

    private String creator;   // 채팅방 생성자 이름

    private String password;  // 비밀번호 (삭제 시 사용할 거)
}