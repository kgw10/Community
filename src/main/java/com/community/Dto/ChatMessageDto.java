package com.community.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String message;
    private String sender; // 메시지를 보낸 사용자 이름 추가
    private LocalDateTime timestamp;


    public void sendMessage(String messageContent, String currentUser, Long chatRoomId) {
        ChatMessageDto message = new ChatMessageDto();
        message.setMessage(messageContent);
        message.setSender(currentUser); // 여기서 currentUser가 올바른지 확인
        message.setTimestamp(LocalDateTime.now());

        // 메시지를 저장하는 로직 (예: DB에 저장)
        // 저장 후 메시지를 가져오는 로직도 필요할 수 있음
    }
}