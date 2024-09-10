package com.community.Dto;

import com.community.Entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 생성
public class ChatRoomDto {
    private Long id;         // 채팅방 ID
    private String title;    // 채팅방 제목
    private String creator;  // 채팅방 생성자 (이름)
    private String password; // 비밀번호 (선택적)

    private static ModelMapper modelMapper = new ModelMapper();

    public ChatRoom createChatRoom() {
        return modelMapper.map(this, ChatRoom.class);
    }

    // Entity -> Dto
    public static ChatRoomDto of(ChatRoom chatRoom) {
        return new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getTitle(),
                chatRoom.getCreator(),
                chatRoom.getPassword() // 필요 시 비밀번호 포함
        );
    }

    // Dto -> Entity
    public ChatRoom chatRoom() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(this.getId());
        chatRoom.setTitle(this.getTitle());
        chatRoom.setCreator(this.getCreator());
        chatRoom.setPassword(this.getPassword());
        return chatRoom;
    }
}