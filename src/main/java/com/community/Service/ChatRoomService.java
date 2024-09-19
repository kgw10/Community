
package com.community.Service;

import com.community.Dto.ChatRoomDto;
import com.community.Entity.ChatRoom;
import com.community.Repository.ChatMessageRepository;
import com.community.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 채팅방을 찾을 수 없습니다: " + id));
    }

    @Transactional
    public void deleteChatRoomById(Long id) {
        chatMessageRepository.deleteByChatRoomId(id);

        if (chatRoomRepository.existsById(id)) {
            chatRoomRepository.deleteById(id);
        } else {
            throw new RuntimeException("해당 ID의 채팅방을 찾을 수 없습니다: " + id);
        }
    }

    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(chatRoomDto.getTitle());
        chatRoom.setCreator(chatRoomDto.getCreator());
        chatRoom.setPassword(chatRoomDto.getPassword());

        return chatRoomRepository.save(chatRoom);
    }

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
