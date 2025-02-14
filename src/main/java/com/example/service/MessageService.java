package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;
    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Creates a message.
     * @param message a transient message entity.
     * @return a persisted message entity.
     */
    public Message createMessage(Message message){
        if (accountRepository.findById(message.getPostedBy()).isEmpty()) {
            throw new IllegalStateException("Account does not exist");
        } else if (message.getMessageText().strip().length() == 0 || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message length invalid");
        } else {
            return messageRepository.save(message);
        }
    }

    /**
     * Retrieves list of all messages.
     * @return a list of message entities.
     */
    public List<Message> retrieveAllMessages(){
        return (List<Message>)messageRepository.findAll();
    }

    /**
     * Retrieves a message by its id.
     * @param messageId the id of a transient message entity.
     * @return a message entity.
     */
    public Message retrieveMessageById(Integer messageId){
        if (messageRepository.findById(messageId).isEmpty()) {
            throw new IllegalStateException("Message does not Exist");
        } else {
            return messageRepository.findById(messageId).get();
        }
    }

    /**
     * Deletes a message by its id.
     * @param messageId the id of a transient message entity.
     * @return true if message was succesfully deleted and false otherwise.
     */
    public Boolean deleteMessageById(Integer messageId){
        if (messageRepository.findById(messageId).isEmpty()) {
            return false;
        } else {
            messageRepository.deleteById(messageId);
            return true;
        }
    }

    /**
     * Updates a message's text by its id.
     * @param message a transient message entity.
     * @param messageId the id of a transient message entity.
     * @return true if the update was succesful and false otherwise.
     */
    public Integer updateMessageById(Message message, Integer messageId){
        if (messageRepository.findById(messageId).isEmpty()) {
            throw new IllegalStateException("Account does not exist");
        } else if (message.getMessageText().strip().length() == 0 || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message length invalid");
        } else {
            Message oldMessage = messageRepository.findById(messageId).get();
            oldMessage.setMessageText(message.getMessageText());
            messageRepository.save(messageRepository.findById(messageId).get());
            return 1;
        }
    }

    /**
     * Retrieves list of all messages from a particular user.
     * @param accountId the id of a transient account entity.
     * @return a list of message entities.
     */
    public List<Message> retrieveAllMessagesFromUser(Integer accountId){
        return (List<Message>)messageRepository.findMessagesByPostedBy(accountId);
    }
    
}
