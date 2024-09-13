package com.community.Control;

import com.community.Dto.ChatMessageDto;
import com.community.Entity.ChatMessage;
import com.community.Repository.ChatMessageRepository;
import com.community.Service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatMessageRepository chatMessageRepository;

    @PostMapping("/{chatRoomId}/message")
    public String sendMessage(@PathVariable Long chatRoomId, @RequestParam String message) {
        chatMessageService.saveMessage(chatRoomId, message);
        return "redirect:/chatroom/" + chatRoomId;
    }

    @GetMapping("/{chatRoomId}/messages")
    public String getMessages(@PathVariable Long chatRoomId, Model model) {
        List<ChatMessageDto> messages = chatMessageService.getMessages(chatRoomId);
        model.addAttribute("messages", messages);
        return "chatroom/messages";
    }

    @GetMapping("/{chatRoomId}")
    public String chatRoom(@PathVariable Long chatRoomId, Model model) {
        List<ChatMessageDto> messages = chatMessageService.getMessages(chatRoomId);
        model.addAttribute("messages", messages);
        model.addAttribute("chatRoomId", chatRoomId);
        return "chatroom/chatRoom"; // 채팅방 상세 페이지 템플릿
    }

    @DeleteMapping("/{chatRoomId}/message/{messageId}/delete")
    public ResponseEntity<?> deleteMessage(@PathVariable Long chatRoomId, @PathVariable Long messageId) {
        try {
            chatMessageService.deleteMessage(chatRoomId, messageId);
            return ResponseEntity.ok().body(Collections.singletonMap("status", "success"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("status", "not_found"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("status", "bad_request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("status", "error"));
        }
    }

    @GetMapping("/chatmessages/{id}")
    @ResponseBody
    public ResponseEntity<ChatMessage> getMessage(@PathVariable Long id) {
        ChatMessage message = chatMessageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}