package com.community.Service;

import com.community.Dto.ChatRoomDto;
import com.community.Entity.ChatRoom;
import com.community.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public void createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(chatRoomDto.getTitle());
        chatRoom.setCreator(chatRoomDto.getCreator());
        chatRoom.setPassword(chatRoomDto.getPassword());

        chatRoomRepository.save(chatRoom);
    }

    // 채팅방 목록 가져오기
    public List<ChatRoomDto> getAllChatRooms() {
        return chatRoomRepository.findAll().stream().map(chatRoom -> {
            ChatRoomDto dto = new ChatRoomDto();
            dto.setId(chatRoom.getId());
            dto.setTitle(chatRoom.getTitle());
            dto.setCreator(chatRoom.getCreator());
            return dto;
        }).collect(Collectors.toList());
    }


    public List<ChatRoomDto> searchChatRooms(String keyword) {
        return chatRoomRepository.findByTitleContainingIgnoreCase(keyword).stream().map(chatRoom -> {
            ChatRoomDto dto = new ChatRoomDto();
            dto.setId(chatRoom.getId());
            dto.setTitle(chatRoom.getTitle());
            dto.setCreator(chatRoom.getCreator());
            return dto;
        }).collect(Collectors.toList());
    }
}