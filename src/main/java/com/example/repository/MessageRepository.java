package com.example.repository;

import java.util.Optional;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA Repository interface for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Updates a message text by its message id
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    Optional<Message> setMessageTextByMessageId(@Param("messageText") String messageText, @Param("messageId") Integer messageId);

    // Finds all messages by a particular user
    Iterable<Message> findMessagesByPostedBy(Integer postedBy);

}
