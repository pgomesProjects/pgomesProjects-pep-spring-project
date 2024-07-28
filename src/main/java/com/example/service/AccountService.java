package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /***
     * Registers a user to the server.
     * @param newAccount The information for the new account.
     * @return The new account information.
     */
    public Optional<Account> registerUser(Account newAccount){

        //If the username is blank or the password has a length less than 4, throw an exception with a status code of 400 (Client Error)
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)
            throw new ClientException("Username or password input invalid.", 400);

        //If the username already exists in the server, throw an exception with a status code of 409 (Conflict)
        if (accountRepository.existsByUsername(newAccount.getUsername()))
            throw new ClientException("Username already exists.", 409);
        
        //Save to the server and return the information
        accountRepository.save(newAccount);
        return Optional.of(newAccount);
    }

    /***
     * Finds an account on the server.
     * @param accountInfo The account information to check for in the server.
     * @return The account information stored in the server.
     */
    public Optional<Account> loginAccount(Account accountInfo){
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(accountInfo.getUsername(), accountInfo.getPassword());

        //If an account with the username and password given has been found, return it
        if(optionalAccount.isPresent())
            return optionalAccount;
        
        //If no user has been found, throw an exception with a status code of 401 (Unauthorized)
        throw new ClientException("Invalid username or password.", 401);
    }
}
