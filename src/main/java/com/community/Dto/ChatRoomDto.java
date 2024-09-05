package com.community.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {
    private Long id;         // 채팅방 ID
    private String title;    // 채팅방 제목
    private String creator;  // 채팅방 생성자 (이름)
    private String password; // 비밀번호 (삭제 시 사용)
}