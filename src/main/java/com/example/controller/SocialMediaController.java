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

    @PostMapping("register")
    public @ResponseBody ResponseEntity<?> registerUser(@RequestBody Account newUser){
        if(accountService.checkForExistingUser(newUser.getUsername()))
            return ResponseEntity.status(409).body("Username already exists.");
        
        Optional<Account> optionalAccount = accountService.registerUser(newUser);
        if(optionalAccount.isPresent())
            return ResponseEntity.ok(optionalAccount.get());

        return ResponseEntity.badRequest().body("Account not registered.");
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<?> loginUser(@RequestBody Account accountInfo){

        Optional<Account> optionalAccount = accountService.loginUser(accountInfo);

        if(optionalAccount.isEmpty())
            return ResponseEntity.status(401).body("Invalid username or password.");

        return ResponseEntity.ok(optionalAccount.get());
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<?> createNewMessage(@RequestBody Message newMessage){

        Optional<Message> optionalMessage = messageService.createMessage(newMessage);
        if(optionalMessage.isPresent())
            return ResponseEntity.ok(optionalMessage.get());

        return ResponseEntity.badRequest().body("Message not created.");
    }

    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> getMessageFromId(@PathVariable int message_id){
        Optional<Message> optionalMessage = messageService.getMessageFromId(message_id);

        if(optionalMessage.isPresent())
            return ResponseEntity.ok(optionalMessage.get());

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> deleteMessageFromId(@PathVariable int message_id){
        int rowsUpdated = messageService.deleteMessage(message_id);

        if(rowsUpdated > 0)
            return ResponseEntity.ok(rowsUpdated);
        
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<?> updateMessageFromId(@PathVariable int message_id, @RequestBody Message newMessage){

        int rowsUpdated = messageService.updateMessage(message_id, newMessage.getMessageText());

        if(rowsUpdated > 0)
            return ResponseEntity.ok(rowsUpdated);
        
        return ResponseEntity.badRequest().body("Message could not updated.");
    }

    @GetMapping("accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesFromAccountId(@PathVariable int account_id){
        return ResponseEntity.ok(messageService.getAllMessagesFromAccountId(account_id));
    }
}
