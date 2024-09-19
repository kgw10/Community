package com.community.Control;

import com.community.Dto.ChatMessageDto;
import com.community.Dto.ChatRoomDto;
import com.community.Entity.ChatRoom;
import com.community.Service.ChatMessageService;
import com.community.Service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/{id}")
    public String viewChatRoom(@PathVariable("id") Long id, Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        ChatRoom chatRoom = chatRoomService.findById(id);
        List<ChatMessageDto> messages = chatMessageService.getMessages(id); // 메시지 가져오기

        if (chatRoom == null) {
            return "redirect:/chatroom/manage";
        }

        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages); // 모델에 메시지 추가
        model.addAttribute("currentUser", userName);
        return "chatroom/chatRoom";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteChatRoom(@PathVariable("id") Long id, @RequestParam("password") String password, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        ChatRoom chatRoom = chatRoomService.findById(id);

        if (chatRoom != null && chatRoom.getCreator().equals(userName)) {
            if (password.equals(chatRoom.getPassword())) {
                chatRoomService.deleteChatRoomById(id);
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 틀렸습니다.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅방이 존재하지 않거나 권한이 없습니다.");
    }

    @GetMapping("/nameRegistration")
    public String showNameRegistrationForm() {
        return "chatroom/nameRegistration";
    }

    @PostMapping("/nameRegistration")
    public String registerName(@RequestParam("userName") String userName, HttpSession session) {
        session.setAttribute("userName", userName);
        return "redirect:/chatroom/manage";
    }

    @GetMapping("/manage")
    public String manageChatRooms(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            return "redirect:/chatroom/nameRegistration";
        }

        model.addAttribute("chatRooms", chatRoomService.getAllChatRooms());
        model.addAttribute("chatRoomDto", new ChatRoomDto());

        return "chatroom/manage";
    }

    @GetMapping("/create")
    public String createChatroomForm() {
        return "chatroom/createChatroom";
    }

    @PostMapping("/create")
    public String createChatRoom(@ModelAttribute ChatRoomDto chatRoomDto, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        if (userName != null) {
            chatRoomDto.setCreator(userName);
            chatRoomDto.setPassword(chatRoomDto.getPassword());

            ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoomDto);
            return "redirect:/chatroom/" + createdChatRoom.getId();
        }
        return "redirect:/chatroom/manage";
    }

    @GetMapping("/search")
    public String searchChatRooms(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            return "redirect:/chatroom/nameRegistration";
        }

        model.addAttribute("chatRooms", chatRoomService.searchChatRooms(keyword));
        return "chatroom/manage";
    }
}