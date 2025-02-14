package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Persist a new user.
     * @param account a transient account entity.
     * @return a persisted account entity.
     */
    public Account registerUser(Account account){
        if (!accountRepository.findAccountByUsername(account.getUsername()).isEmpty()) {
            throw new IllegalStateException("Username already exists");
        } else if (account.getUsername().strip().length() == 0 || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Username and/or Password are invalid");
        } else { 
            return accountRepository.save(account);
        }
    }

    /**
     * Checks a users login credentials.
     * @param account a transient account entity.
     * @return a persisted account entity.
     */
    public Account userLogin(Account account){
        if (accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword()).isEmpty()) {
            throw new IllegalArgumentException("Username and/or Password do not match an existing Account");
        } else { 
            return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword()).get();
        }
    }

}
