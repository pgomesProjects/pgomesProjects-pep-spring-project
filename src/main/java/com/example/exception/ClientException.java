package com.example.exception;

public class ClientException extends RuntimeException {

    //A simple custom exception that holds a message and an HTTP status code
    private String message;
    private int statusCode;

    public ClientException(String message, int statusCode){
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage(){
        return message;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
