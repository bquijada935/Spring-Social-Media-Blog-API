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
        if (account.getUsername().strip().length() == 0) {
            return null;
        } else if (account.getPassword().length() < 4) {
            return null;
        } else if (accountRepository.findAccountByUsername(account.getUsername()) != null) {
            return account;
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
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword()).get();
    }
    
}
