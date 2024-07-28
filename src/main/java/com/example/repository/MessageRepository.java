package com.example.repository;

import com.example.entity.Message;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//Utilizes the JPARepository method signatures to perform CRUD tasks automatically for the message database
public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findByPostedBy(int accountId);     //Finds any accounts that have an account id equal to the one provided (returns a list of messages found)
}
