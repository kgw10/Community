package com.community.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
}