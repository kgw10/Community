package com.community.Service;

import com.community.Dto.ChatMessageDto;
import com.community.Entity.ChatMessage;
import com.community.Entity.ChatRoom;
import com.community.Repository.ChatMessageRepository;
import com.community.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveMessage(Long chatRoomId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageDto> getMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        return chatMessageRepository.findByChatRoom(chatRoom).stream().map(chatMessage -> {
            ChatMessageDto dto = new ChatMessageDto();
            dto.setId(chatMessage.getId());
            dto.setMessage(chatMessage.getMessage());
            dto.setTimestamp(chatMessage.getTimestamp());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteMessage(Long chatRoomId, Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getChatRoom().getId().equals(chatRoomId)) {
            throw new RuntimeException("Message does not belong to this chat room");
        }

        chatMessageRepository.deleteById(messageId);
    }

    @Transactional(readOnly = true)
    public ChatMessage getMessageById(Long messageId) {
        return chatMessageRepository.findByIdCustom(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
}