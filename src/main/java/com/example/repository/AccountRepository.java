package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository interface for the Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
