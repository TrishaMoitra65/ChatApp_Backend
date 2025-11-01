package com.example.chatApp.service;

import com.example.chatApp.Model.Chat;
import com.example.chatApp.Model.Message;
import com.example.chatApp.repositories.MessageRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    // ✅ Send message immediately
    public Message sendMessage(String content, String sender, Chat chat) {
        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .chat(chat)
                .sent(true)
                .sentTime(LocalDateTime.now())
                .build();

        return messageRepo.save(message);
    }

    // ✅ Schedule message for future
    public Message scheduleMessage(String content, String sender, Chat chat, LocalDateTime scheduledTime) {
        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .chat(chat)
                .scheduledTime(scheduledTime)
                .sent(false)
                .build();

        return messageRepo.save(message);
    }

    // ✅ Fetch only visible messages for chat (no future messages)
    public List<Message> getMessagesByChat(Chat chat) {
        LocalDateTime now = LocalDateTime.now();
        return messageRepo.findAll().stream()
                .filter(m -> m.getChat().getId().equals(chat.getId()))
                .filter(m ->
                        // Show sent messages
                        m.isSent() ||
                        // Show scheduled messages only if time has arrived or passed
                        (m.getScheduledTime() != null && !m.getScheduledTime().isAfter(now))
                )
                .toList();
    }

    // ✅ Fetch messages that are due to be sent (for scheduler job)
    public List<Message> getDueMessages() {
        return messageRepo.findDueMessages(LocalDateTime.now());
    }

    // ✅ Mark message as sent and set sent time
    public void markAsSent(Message message) {
        message.setSent(true);
        message.setSentTime(LocalDateTime.now());
        messageRepo.save(message);
    }
}
