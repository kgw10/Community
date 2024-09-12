package com.community.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String message;
    private LocalDateTime timestamp;
}