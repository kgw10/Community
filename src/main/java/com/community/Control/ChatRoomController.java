package com.community.Control;

import com.community.Dto.ChatRoomDto;
import com.community.Service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public String listChatrooms(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            // 이름이 없으면 이름 등록 페이지로 리다이렉트
            return "redirect:/nameRegistration";
        }
        model.addAttribute("chatRooms", chatRoomService.getAllChatRooms());
        return "chatroom/chatList";
    }

    @GetMapping("/create")
    public String createChatRoomForm(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/nameRegistration";
        }
        model.addAttribute("chatRoomDto", new ChatRoomDto());
        return "chatroom/createChatroom";
    }

    @PostMapping("/create")
    public String createChatRoom(@ModelAttribute ChatRoomDto chatRoomDto, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            chatRoomDto.setCreator(userName);
            chatRoomService.createChatRoom(chatRoomDto);
        }
        return "redirect:/chatroom";
    }

    @PostMapping("/saveName")
    @ResponseBody
    public void saveName(@RequestParam String name, HttpSession session) {
        session.setAttribute("userName", name);
    }


    @GetMapping("/search")
    public String searchChatRooms(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/nameRegistration";
        }
        model.addAttribute("chatRooms", chatRoomService.searchChatRooms(keyword));
        return "chatroom/chatList";
    }

}