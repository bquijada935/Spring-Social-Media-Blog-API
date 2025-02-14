package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

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
     * Creates a new Account on the endpoint POST localhost:8080/register.
     * @param account a transient account entity.
     * @return a response status and response body.
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
     * Verifies a user's login on the endpoint POST localhost:8080/login.
     * @param account a transient account entity.
     * @return a response status and response body.
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
     * Submits a new post on the endpoint POST localhost:8080/messages.
     * @param message a transient message entity.
     * @return a response status and response body.
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
     * Submits a GET request on the endpoint GET localhost:8080/messages.
     * @return a response status and response body.
     */
    @GetMapping("/messages")
    public ResponseEntity getAllMessagesHandler() {
        return ResponseEntity.status(200).body(messageService.retrieveAllMessages());
    }

    /**
     * Submits a GET request on the endpoint GET localhost:8080/messages/{messageId}.
     * @param messsageId the id of a transient message entity.
     * @return a response status and response body.
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
     * Submits a DELETE request on the endpoint DELETE localhost:8080/messages/{messageId}.
     * @param messageId the id of a transient message entity.
     * @return a response status and response body.
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
     * Submits a PATCH request on the endpoint PATCH localhost:8080/messages/{messageId}.
     * @param message a transient message entity.
     * @param messageId the id of a transient message entity.
     * @return a response status and response body.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessageByIdHandler(@RequestBody Message message, @PathVariable Integer messageId) {
        try {
            Integer updated = messageService.updateMessageById(message, messageId);
            return ResponseEntity.status(200).body(updated);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    /**
     * Submits a GET request on the endpoint GET localhost:8080/accounts/{accountId}/messages.
     * @param accountId the id of a transient account entity.
     * @return a response status and response body.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getMessagesByAccountIdHandler(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(messageService.retrieveAllMessagesFromUser(accountId));
    }

}
