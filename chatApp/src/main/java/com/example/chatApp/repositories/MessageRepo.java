package com.example.chatApp.repositories;

import com.example.chatApp.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    // ✅ Fetch only messages that are due AND not sent yet
    @Query("SELECT m FROM Message m WHERE m.scheduledTime <= :now AND m.sent = false")
    List<Message> findDueMessages(LocalDateTime now);

    // ✅ Optional helper if needed elsewhere
    List<Message> findByScheduledTimeBeforeAndSentFalse(LocalDateTime time);
}
