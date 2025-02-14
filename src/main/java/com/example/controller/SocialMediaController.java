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
        Account registeredAccount = accountService.registerUser(account);
        if (registeredAccount == null) {
            return ResponseEntity.status(400).body("Client error");
        } else if (registeredAccount != account) {
            return ResponseEntity.status(409).body("Conflict");
        } else {
            return ResponseEntity.status(200).body(registeredAccount);
        }
    }

    /**
     * [...]
     */
    @PostMapping("/login")
    public ResponseEntity postUserLoginHandler(@RequestBody Account account) {
        Account registeredAccount = accountService.userLogin(account);
        if (registeredAccount == null) {
            return ResponseEntity.status(401).body("Unsuccessful Login");
        } else {
            return ResponseEntity.status(200).body(registeredAccount);
        }
    }

    /**
     * [...]
     */
    @PostMapping("/messages")
    public ResponseEntity postMessageCreationHandler(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage == null) {
            return ResponseEntity.status(400).body("Client Error");
        } else {
            return ResponseEntity.status(200).body(createdMessage);
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
        return ResponseEntity.status(200).body(messageService.retrieveMessageById(messageId));
    }

    /**
     * [...]
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageByIdHandler(@PathVariable Integer messageId) {
        if (messageService.deleteMessageById(messageId)) {
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(200).body(0);
        }
    }

    /**
     * [...]
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessageByIdHandler(@RequestParam String messageText, @PathVariable Integer messageId) {
        if (messageService.updateMessageById(messageText, messageId)) {
            return ResponseEntity.status(200).body(1);
        } else {
            return ResponseEntity.status(400).body(0);
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
