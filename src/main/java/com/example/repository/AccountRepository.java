package com.example.repository;

import com.example.entity.Account;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//Utilizes the JPARepository method signatures to perform CRUD tasks automatically for the account database
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public boolean existsByUsername(String username);   //Checks if the username given exists in the database (returns true or false)
    public Optional<Account> findByUsernameAndPassword(String username, String password); //Checks the database for any accounts where the account has both the username and password combination given (returns either the account information found or null)
}
