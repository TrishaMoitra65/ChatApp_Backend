package com.example.chatApp.Model;

import com.example.chatApp.Model.Chat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String sender;
    private LocalDateTime sentTime; // when actually sent
    private LocalDateTime scheduledTime; // for scheduling
    private boolean sent = false;

   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "chat_id")
    private Chat chat;

    public boolean isScheduled() {
        return scheduledTime != null;
    }

    public boolean isReadyToSend() {
        return !sent && scheduledTime != null && scheduledTime.isBefore(LocalDateTime.now());
    }
}
