package com.example.chatApp.repositories;

import com.example.chatApp.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat, Long> {

    // ✅ Find chat by unique code
    Optional<Chat> findByChatCode(String chatCode);

    // ✅ Find chat between two users (to avoid duplicate chat creation)
    Optional<Chat> findByUser1_IdAndUser2_Id(Long user1Id, Long user2Id);

    // ✅ Get all chats where user is either user1 or user2
    @Query("SELECT c FROM Chat c WHERE c.user1.username = :username OR c.user2.username = :username")
    List<Chat> findChatsByUsername(@Param("username") String username);
}
