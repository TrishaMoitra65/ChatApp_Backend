package com.example.chatApp.service;

import com.example.chatApp.Model.Message;
import com.example.chatApp.repositories.MessageRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    private final MessageRepo messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public SchedulerService(MessageRepo messageRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // ✅ Run every 5 seconds, but only send messages whose time has *definitely passed*
    @Scheduled(fixedDelay = 5000)
    public void checkAndSendScheduledMessages() {
        LocalDateTime now = LocalDateTime.now();

        // ✅ Fetch only messages that are due and unsent
        List<Message> dueMessages = messageRepository.findDueMessages(now);

        for (Message message : dueMessages) {
            try {
                // ✅ Extra safeguard: skip if scheduledTime is still in the future
                if (message.getScheduledTime() != null && message.getScheduledTime().isAfter(now)) {
                    continue;
                }

                // ✅ Mark message as sent
                message.setSent(true);
                message.setSentTime(LocalDateTime.now());
                messageRepository.save(message);

                // ✅ Send to WebSocket subscribers
                messagingTemplate.convertAndSend(
                        "/topic/messages/" + message.getChat().getId(),
                        message
                );

                System.out.println("📩 Scheduled message sent at " + LocalDateTime.now() +
                                   " | Scheduled for: " + message.getScheduledTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
