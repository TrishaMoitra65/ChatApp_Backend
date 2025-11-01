package com.example.chatApp.controller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleMessageRequest {
    private Long chatId;
    private String sender;
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
     private LocalDateTime scheduledTime;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
