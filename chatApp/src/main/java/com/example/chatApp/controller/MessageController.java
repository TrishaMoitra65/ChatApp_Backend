package com.example.chatApp.controller;

import com.example.chatApp.Model.Chat;
import com.example.chatApp.Model.Message;
import com.example.chatApp.service.ChatService;
import com.example.chatApp.repositories.*;
import com.example.chatApp.service.MessageService;
import com.example.chatApp.controller.ScheduleMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
     private final ChatRepo chatRepository;
    private final MessageRepo messageRepository;

    public MessageController(MessageService messageService, ChatService chatService, ChatRepo chatRepository,MessageRepo messageRepository) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.chatRepository=chatRepository;
        this.messageRepository=messageRepository;
    }

    // ✅ Send message instantly
  @PostMapping("/send")
public Message sendMessage(@RequestBody SendMessageRequest request) {
    Chat chat = chatService.getChatById(request.getChatId());
    return messageService.sendMessage(request.getContent(), request.getSender(), chat);
}


    // ✅ Schedule a message for future
@PostMapping("/schedule")
public ResponseEntity<Message> scheduleMessage(@RequestBody ScheduleMessageRequest request) {
    System.out.println("🟢 Schedule API hit with request: " + request);

    Chat chat = chatRepository.findById(request.getChatId())
            .orElseThrow(() -> new RuntimeException("Chat not found for ID: " + request.getChatId()));

    Message message = new Message();
    message.setChat(chat);
    message.setContent(request.getContent());
    message.setSender(request.getSender());
    message.setScheduledTime(request.getScheduledTime());
    message.setSent(false);

    messageRepository.save(message);
    System.out.println("✅ Message scheduled successfully: " + message);

    // ❌ REMOVE this line — it causes the early display
    // messagingTemplate.convertAndSend("/topic/chat/" + chat.getId(), message);

    return ResponseEntity.ok(message);
}

    // ✅ Get all messages in a chat
    @GetMapping("/chat/{chatId}")
    public List<Message> getMessagesByChat(@PathVariable Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        return messageService.getMessagesByChat(chat);
    }
} 

