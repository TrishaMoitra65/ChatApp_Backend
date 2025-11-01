package com.example.chatApp.controller;

import com.example.chatApp.Model.Chat;
import com.example.chatApp.Model.User;
import com.example.chatApp.service.ChatService;
import com.example.chatApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    /**
     * ✅ Create or get existing private chat between two users
     */
    @PostMapping("/create")
    public Chat createPrivateChat(@RequestParam String senderUsername,
                                  @RequestParam String receiverUsername) {

        // Ensure both users exist
        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findByUsername(receiverUsername);

        if (sender == null || receiver == null) {
            throw new RuntimeException("One or both users not found.");
        }

        // Create or return existing chat
        return chatService.createPrivateChat(sender, receiver);
    }

    /**
     * ✅ Join chat using a unique chat code
     */
    @GetMapping("/join/{code}")
    public Chat joinChat(@PathVariable String code) {
        return chatService.findByChatCode(code);
    }

    /**
     * ✅ Get all chats (admin/debugging)
     */
    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    /**
     * ✅ Get all chats for a specific user
     */
    @GetMapping("/user/{username}")
    public List<Chat> getChatsForUser(@PathVariable String username) {
        return chatService.getChatsForUser(username);
    }
}
