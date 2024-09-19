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



}