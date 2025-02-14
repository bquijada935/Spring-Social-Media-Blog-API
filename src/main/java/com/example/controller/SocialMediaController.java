package com.example.controller;

import java.util.Optional;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * [...]
     */
    @PostMapping("/register")
    public ResponseEntity postUserRegistrationHandler(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerUser(account);
            return ResponseEntity.status(200).body(registeredAccount);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    /**
     * [...]
     */
    @PostMapping("/login")
    public ResponseEntity postUserLoginHandler(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.userLogin(account);
            return ResponseEntity.status(200).body(registeredAccount);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }

    /**
     * [...]
     */
    @PostMapping("/messages")
    public ResponseEntity postMessageCreationHandler(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(200).body(createdMessage);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * [...]
     */
    @GetMapping("/messages")
    public ResponseEntity getAllMessagesHandler() {
        return ResponseEntity.status(200).body(messageService.retrieveAllMessages());
    }

    /**
     * [...]
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageByIdHandler(@PathVariable Integer messageId) {
        try {
            Message message = messageService.retrieveMessageById(messageId);
            return ResponseEntity.status(200).body(message);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(200).build();
        }
    }

    /**
     * [...]
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageByIdHandler(@PathVariable Integer messageId) {
        if (messageService.deleteMessageById(messageId)) {
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    /**
     * [...]
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessageByIdHandler(@RequestParam String messageText, @PathVariable Integer messageId) {
        try {
            messageService.updateMessageById(messageText, messageId);
            return ResponseEntity.status(200).body(1);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    /**
     * [...]
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getMessagesByAccountIdHandler(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(messageService.retrieveAllMessagesFromUser(accountId));
    }

}
