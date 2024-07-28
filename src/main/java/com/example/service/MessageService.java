package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ClientException;
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

    /***
     * Creates a message onto the server.
     * @param newMessage The message information to add to the server.
     * @return The message information as an object.
     */
    public Optional<Message> createMessage(Message newMessage){
        //If the message is blank, is larger than or equal to 255 characters, or the user associated with the message does not exist, throw an exception with the status code of 400 (Client Error)
        if(newMessage.getMessageText().isBlank() || newMessage.getMessageText().length() >= 255 || !accountRepository.existsById((newMessage.getPostedBy())))
            throw new ClientException("Invalid message details.", 400);

        //Save the message to the server and return it
        messageRepository.save(newMessage);   
        return Optional.of(newMessage);
    }

    /***
     * Returns a list of all messages stored in the server.
     * @return All of the messages in the server stored in a list.
     */
    public List<Message> getAllMessages(){
        return(List<Message>) messageRepository.findAll();
    }

    /***
     * Gets a message from the server based on the id given.
     * @param messageId The id of the message to check for.
     * @return The message information as an object.
     */
    public Optional<Message> getMessageFromId(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        //If the message has been found, return it
        if(optionalMessage.isPresent())
            return optionalMessage;

        //If no message has been found, throw an exception with the status of 200 (OK, but has no message information to send)
        throw new ClientException("", 200);
    }

    /***
     * Deletes a message from the server based on the id given.
     * @param messageId The id of the message to check for.
     * @return The amount of rows updated in the server.
     */
    public int deleteMessage(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        //If the message has been found, delete the message from the server and then return the amount of updated rows (1)
        if(optionalMessage.isPresent()){
            messageRepository.delete(optionalMessage.get());
            return 1;
        }

        //If no message has been found, throw an exception with the status of 200 (OK, but has no message information to send)
        throw new ClientException("", 200);
    }

    /***
     * Updates a message from the server with new information based on the id given.
     * @param messageId The id of the message to update.
     * @param newMessage The new contents of the message.
     * @return The amount of rows updated in the server.
     */
    public int updateMessage(int messageId, String newMessage){

        //If the new message is blank or the length is greater than or equal to 255 characters long, throw an exception with the status code of 400 (Client Error)
        if(newMessage.isBlank() || newMessage.length() >= 255)
            throw new ClientException("Invalid message details.", 400);

        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        //If the message has been found, update the message text to the new information given, store the change on the server, and then return the amount of updated rows (1)
        if(optionalMessage.isPresent()){
            optionalMessage.get().setMessageText(newMessage);
            messageRepository.save(optionalMessage.get());
            return 1;
        }

        //If no message has been found, throw an exception with the status of 400 (Client Error)
        throw new ClientException("", 400);
    }

    /***
     * Gets all of the messages associated with the account id given.
     * @param accountId The account id to check for within the message database.
     * @return A list of messages associated with the account id given.
     */
    public List<Message> getAllMessagesFromAccountId(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
