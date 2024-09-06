package com.community.Control;

import com.community.Dto.ChatMessageDto;
import com.community.Service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/{chatRoomId}/message")
    public String sendMessage(@PathVariable Long chatRoomId, @RequestParam String sender, @RequestParam String message) {
        chatMessageService.saveMessage(chatRoomId, sender, message);
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
}