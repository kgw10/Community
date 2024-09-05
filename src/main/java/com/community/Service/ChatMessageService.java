package com.community.Service;

import com.community.Dto.ChatMessageDto;
import com.community.Entity.ChatMessage;
import com.community.Entity.ChatRoom;
import com.community.Repository.ChatMessageRepository;
import com.community.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 메시지 저장
    public void saveMessage(Long chatRoomId, String sender, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
    }

    // 채팅방의 메시지 목록 불러오기
    public List<ChatMessageDto> getMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        return chatMessageRepository.findByChatRoom(chatRoom).stream().map(chatMessage -> {
            ChatMessageDto dto = new ChatMessageDto();
            dto.setId(chatMessage.getId());
            dto.setSender(chatMessage.getSender());
            dto.setMessage(chatMessage.getMessage());
            dto.setTimestamp(chatMessage.getTimestamp());
            return dto;
        }).collect(Collectors.toList());
    }
}