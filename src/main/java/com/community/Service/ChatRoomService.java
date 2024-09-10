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

    // 채팅방 ID로 찾기 (삭제 시 비밀번호 검증에 필요)
    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 채팅방을 찾을 수 없습니다: " + id));
    }


    // 채팅방 삭제
    @Transactional
    public void deleteChatRoomById(Long id) {
        // 채팅방에 연결된 메시지 삭제
        chatMessageRepository.deleteByChatRoomId(id);

        if (chatRoomRepository.existsById(id)) {
            chatRoomRepository.deleteById(id);
        } else {
            throw new RuntimeException("해당 ID의 채팅방을 찾을 수 없습니다: " + id);
        }
    }
    @Transactional
    public void deleteAllChatRooms() {
        // 모든 메시지 삭제
        chatMessageRepository.deleteAll();

        // 모든 채팅방 삭제
        chatRoomRepository.deleteAll();
    }



    // 채팅방 생성
    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(chatRoomDto.getTitle());
        chatRoom.setCreator(chatRoomDto.getCreator());
        chatRoom.setPassword(chatRoomDto.getPassword());

        return chatRoomRepository.save(chatRoom);
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

    // 채팅방 검색
    public List<ChatRoomDto> searchChatRooms(String keyword) {
        return chatRoomRepository.findByTitleContainingIgnoreCase(keyword).stream().map(chatRoom -> {
            ChatRoomDto dto = new ChatRoomDto();
            dto.setId(chatRoom.getId());
            dto.setTitle(chatRoom.getTitle());
            dto.setCreator(chatRoom.getCreator());
            return dto;
        }).collect(Collectors.toList());
    }

    // 채팅방 생성
    public void createChatRoom(String title, String password) {

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(title);
        chatRoom.setPassword(password);
        chatRoomRepository.save(chatRoom);
    }


}