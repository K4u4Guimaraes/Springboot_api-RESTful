package com.example.learnSpring.handlers.advices;

import com.example.learnSpring.handlers.exceptions.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> UserNullCatcherError(){

        Map<String,Object> body = new HashMap<String,Object>();

        body.put("message","user already exists");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
