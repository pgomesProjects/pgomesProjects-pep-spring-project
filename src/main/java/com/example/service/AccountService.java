package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Optional<Account> registerUser(Account newAccount){
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)
            return null;
        
        accountRepository.save(newAccount);
        return Optional.of(newAccount);
    }

    public Optional<Account> loginUser(Account accountInfo){
        return accountRepository.findByUsernameAndPassword(accountInfo.getUsername(), accountInfo.getPassword());
    }

    public boolean checkForExistingUser(String username){
        return accountRepository.existsByUsername(username);
    }
}
