package com.example.repository;

import java.util.Optional;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository interface for the Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Finds an account based on its username
    Optional<Account> findAccountByUsername(String username);

    // Finds an account based on its username and password
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
}
