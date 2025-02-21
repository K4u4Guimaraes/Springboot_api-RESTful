package com.example.learnSpring.handlers.exceptions;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super();
    }


    public UserExistsException(String message) {
        super(message);
    }


}
