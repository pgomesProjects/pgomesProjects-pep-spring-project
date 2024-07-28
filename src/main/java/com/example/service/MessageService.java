package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<Message> createMessage(Message newMessage){
        if(newMessage.getMessageText().isBlank() || newMessage.getMessageText().length() >= 255 || !accountRepository.existsById((newMessage.getPostedBy())))
            return Optional.empty();

        messageRepository.save(newMessage);   
        return Optional.of(newMessage);
    }

    public List<Message> getAllMessages(){
        return(List<Message>) messageRepository.findAll();
    }

    public Optional<Message> getMessageFromId(int messageId){
        return messageRepository.findById(messageId);
    }

    public int deleteMessage(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            messageRepository.delete(optionalMessage.get());
            return 1;
        }
        return 0;
    }

    public int updateMessage(int messageId, String newMessage){

        if(newMessage.isBlank() || newMessage.length() >= 255)
            return 0;

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            optionalMessage.get().setMessageText(newMessage);
            messageRepository.save(optionalMessage.get());
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesFromAccountId(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
