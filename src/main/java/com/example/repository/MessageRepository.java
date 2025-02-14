package com.example.repository;

import java.util.List;
import java.util.Optional;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA Repository interface for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Updates a message text by its message id
    @Query("UPDATE Message m SET m.messageText = ?1 WHERE m.messageId = ?2")
    Optional<Message> updateMessageTextByMessageId(String messageText, Integer messageId);

    // Finds all messages by a particular user
    Iterable<Message> findMessagesByPostedBy(Integer postedBy);

}
