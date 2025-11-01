 package com.example.chatApp.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chatApp.service.MessageService;
import com.example.chatApp.Model.Chat;
import com.example.chatApp.Model.Message;
import com.example.chatApp.service.ChatService;

@Controller
public class ChatWebSocketController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(MessageService messageService, ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void handleChatMessage(@Payload Message incomingMessage) {
        try {
            Long chatId = incomingMessage.getChat().getId();
            String sender = incomingMessage.getSender();
            String content = incomingMessage.getContent();

            Chat chat = chatService.getChatById(chatId);
            Message savedMessage = messageService.sendMessage(content, sender, chat);

            // ✅ Send message only to that specific chat's topic
            messagingTemplate.convertAndSend("/topic/messages/" + chatId, savedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
