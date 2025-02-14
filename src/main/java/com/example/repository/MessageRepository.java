package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository interface for the Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
