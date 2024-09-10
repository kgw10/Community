package com.community.Control;

import com.community.Dto.ChatRoomDto;
import com.community.Entity.ChatRoom;
import com.community.Service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;


    // 채팅방 삭제
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


    // 이름 입력 페이지로 이동
    @GetMapping("/nameRegistration")
    public String showNameRegistrationForm() {
        return "chatroom/nameRegistration"; // 이름 등록 폼 페이지
    }

    // 이름 등록 처리
    @PostMapping("/nameRegistration")
    public String registerName(@RequestParam("userName") String userName, HttpSession session) {
        session.setAttribute("userName", userName);
        // 로그 추가
        System.out.println("userName 저장됨: " + userName);
        return "redirect:/chatroom/manage"; // 채팅방 목록 페이지로 리다이렉트
    }

    // 채팅방 관리 페이지
    @GetMapping("/manage")
    public String manageChatRooms(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        // 이름이 없으면 이름 등록 페이지로 리다이렉트
        if (userName == null) {
            System.out.println("세션에 userName 없음, nameRegistration으로 리다이렉트");
            return "redirect:/chatroom/nameRegistration";
        }

        System.out.println("세션에 userName 있음: " + userName);
        model.addAttribute("chatRooms", chatRoomService.getAllChatRooms());
        model.addAttribute("chatRoomDto", new ChatRoomDto());

        return "chatroom/manage"; // 통합된 페이지 이름
    }

    // 채팅방 생성 페이지
    @GetMapping("/create")
    public String createChatroomForm() {
        return "chatroom/createChatroom"; // 채팅방 생성 페이지
    }

    // 채팅방 생성 (비밀번호 암호화 없이 평문 저장)
    @PostMapping("/create")
    public String createChatRoom(@ModelAttribute ChatRoomDto chatRoomDto, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        // 유저 이름이 세션에 있을 때만 채팅방 생성
        if (userName != null) {
            chatRoomDto.setCreator(userName);

            // 비밀번호 평문 저장 (암호화 없이)
            chatRoomDto.setPassword(chatRoomDto.getPassword());

            ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoomDto);
            return "redirect:/chatroom/" + createdChatRoom.getId();
        }
        return "redirect:/chatroom/manage"; // 관리 페이지로 리다이렉트
    }


    // 채팅방 검색
    @GetMapping("/search")
    public String searchChatRooms(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        // 유저 이름이 없으면 리다이렉트
        if (userName == null) {
            return "redirect:/chatroom/nameRegistration";
        }

        model.addAttribute("chatRooms", chatRoomService.searchChatRooms(keyword));
        return "chatroom/manage"; // 검색 후 통합 페이지로 리다이렉트
    }


}