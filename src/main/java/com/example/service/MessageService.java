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
        if (message.getMessageText().strip().length() == 0 || message.getMessageText().length() > 255) {
            return null;
        } else if (accountRepository.findById(message.getPostedBy()).get() == null) {
            return null;
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
        return messageRepository.findById(messageId).get();
    }

    /**
     * Deletes a message by its id.
     * @param messageId the id of a transient message entity.
     * @return true if message was succesfully deleted and false otherwise.
     */
    public Boolean deleteMessageById(Integer messageId){
        if (messageRepository.findById(messageId) == null) {
            return false;
        } else {
            messageRepository.deleteById(messageId);
            return true;
        }
    }

    /**
     * Updates a message's text by its id.
     * @param messageText the text of a transient message entity.
     * @param messageId the id of a transient message entity.
     * @return true if the update was succesful and false otherwise.
     */
    public Boolean updateMessageById(String messageText, Integer messageId){
        if (messageRepository.findById(messageId) == null) {
            return false;
        } else if (messageText.strip().length() == 0 || messageText.length() > 255) {
            return false;
        } else {
            messageRepository.updateMessageTextByMessageId(messageText, messageId);
            return true;
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
