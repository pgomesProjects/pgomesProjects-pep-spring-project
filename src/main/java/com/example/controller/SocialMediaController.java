package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /***
     * Sends a request to the server to register a new account.
     * @param newAccount The new account information in the Request Body
     * @return A ResponseEntity with the new account information.
     */
    @PostMapping("register")
    public @ResponseBody ResponseEntity<?> registerAccount(@RequestBody Account newAccount){
        Optional<Account> optionalAccount = accountService.registerUser(newAccount);
        return ResponseEntity.ok(optionalAccount.get());
    }

    /***
     * Sends a request to the server to login an existing account.
     * @param accountInfo The account information to attempt to log into.
     * @return A ResponseEntity with the logged in account information.
     */
    @PostMapping("login")
    public @ResponseBody ResponseEntity<?> loginAccount(@RequestBody Account accountInfo){
        Optional<Account> optionalAccount = accountService.loginAccount(accountInfo);
        return ResponseEntity.ok(optionalAccount.get());
    }

    /***
     * Sends a request to the server to create a new message.
     * @param newMessage The information for the new message to create.
     * @return A ResponseEntity with the new message information.
     */
    @PostMapping("messages")
    public @ResponseBody ResponseEntity<?> createNewMessage(@RequestBody Message newMessage){
        Optional<Message> optionalMessage = messageService.createMessage(newMessage);
        return ResponseEntity.ok(optionalMessage.get());
    }

    /***
     * Sends a request to the server to get a list of all messages stored.
     * @return A ResponseEntity with a list of all messages in the server.
     */
    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /***
     * Sends a request to the server to get a message from the server based on the message id given.
     * @param message_id The id of the message to get the information for (retrieved from the URL path).
     * @return A ResponseEntity with the information for the message found.
     */
    @GetMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> getMessageFromId(@PathVariable int message_id){
        Optional<Message> optionalMessage = messageService.getMessageFromId(message_id);
        return ResponseEntity.ok(optionalMessage.get());
    }

    /***
     * Sends a request to the server to delete a message from the server based on the message id given.
     * @param message_id The id of the message to delete from the server (retrieved from the URL path.)
     * @return The number of rows updated in the server (1).
     */
    @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> deleteMessageFromId(@PathVariable int message_id){
        int rowsUpdated = messageService.deleteMessage(message_id);
        return ResponseEntity.ok(rowsUpdated);
    }

    /***
     * Sends a request to the server to update the content of a message on the server based on the message id given.
     * @param message_id The id of the message to update (retrieved from the URL path).
     * @param newMessage The contents of the new message to update with.
     * @return The number of rows updated in the server (1).
     */
    @PatchMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> updateMessageFromId(@PathVariable int message_id, @RequestBody Message newMessage){
        int rowsUpdated = messageService.updateMessage(message_id, newMessage.getMessageText());
        return ResponseEntity.ok(rowsUpdated);
    }

    /***
     * Sends a request to the server to get a list of all messages on the server associated with a specific account id.
     * @param account_id The account id to look for in the server (retrieved from the URL path).
     * @return A ResponseEntity with the list of all messages from the user with the specified account id.
     */
    @GetMapping("accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesFromAccountId(@PathVariable int account_id){
        return ResponseEntity.ok(messageService.getAllMessagesFromAccountId(account_id));
    }
}
