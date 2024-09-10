package com.community.Repository;

import com.community.Entity.ChatMessage;
import com.community.Entity.ChatRoom;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    void deleteByChatRoomId(Long chatRoomId);
}