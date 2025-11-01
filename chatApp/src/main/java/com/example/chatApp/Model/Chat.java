package com.example.chatApp.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The two users participating in the chat
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    // A unique code that can be shared to join the chat
    @Column(unique = true)
    private String chatCode;

    public Chat() {}

    public Chat(User user1, User user2, String chatCode) {
        this.user1 = user1;
        this.user2 = user2;
        this.chatCode = chatCode;
    }

    // ✅ Minimal necessary getters/setters only
    public Long getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getChatCode() {
        return chatCode;
    }

    public void setChatCode(String chatCode) {
        this.chatCode = chatCode;
    }
}
