package com.community.Repository;

import com.community.Entity.ChatMessage;
import com.community.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    void deleteByChatRoomId(Long chatRoomId);
    void deleteById(Long id);

    @Query("SELECT c FROM ChatMessage c WHERE c.id = :id")
    Optional<ChatMessage> findByIdCustom(@Param("id") Long id);
}