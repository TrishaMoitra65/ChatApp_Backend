package com.example.chatApp.controller;

import java.time.LocalDateTime;

public class MessageDto {
    private String content;
    private String sender;
    private String receiver;
    private Long chatId;
    private LocalDateTime scheduledTime; // for scheduling message

    public MessageDto() {
    }

    public MessageDto(String content, String sender, String receiver, Long chatId, LocalDateTime scheduledTime) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.chatId=chatId;
        this.scheduledTime = scheduledTime;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }


    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }


}
