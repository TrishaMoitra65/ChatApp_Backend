package com.example.chatApp.service;

import com.example.chatApp.Model.Chat;
import com.example.chatApp.Model.User;
import com.example.chatApp.repositories.ChatRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepo chatRepository;
    private final UserService userService;

    public ChatService(ChatRepo chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    // ✅ Create or return existing private chat between two users
    public Chat createPrivateChat(User sender, User receiver) {
        // Check both directions (A→B or B→A)
        Optional<Chat> existingChat = chatRepository
                .findByUser1_IdAndUser2_Id(sender.getId(), receiver.getId())
                .or(() -> chatRepository.findByUser1_IdAndUser2_Id(receiver.getId(), sender.getId()));

        if (existingChat.isPresent()) {
            return existingChat.get();
        }

        // Generate unique chat code
        String chatCode = UUID.randomUUID().toString().substring(0, 8);

        Chat chat = new Chat(sender, receiver, chatCode);
        return chatRepository.save(chat);
    }

    // ✅ Find chat by unique code
    public Chat findByChatCode(String code) {
        return chatRepository.findByChatCode(code)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    // ✅ Find chat by ID
    public Chat getChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    // ✅ Get all chats for a specific user
    public List<Chat> getChatsForUser(String username) {
        return chatRepository.findChatsByUsername(username);
    }

    // ✅ Get all chats (for admin/debugging)
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }
}
