package com.example.learnSpring.handlers.advices;

import com.example.learnSpring.handlers.exceptions.ProductNullPointerException;
import com.example.learnSpring.handlers.exceptions.ProductPriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNullPointerException.class)
    public ResponseEntity<Object> ProductNullCatcherError(){

        Map<String,Object> body = new HashMap<String,Object>();

        body.put("message","Product not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


    @ExceptionHandler(ProductPriceException.class)
    public ResponseEntity<Object> ProductPriceCatcherError(){

        Map<String,Object> body = new HashMap<String,Object>();

        body.put("message","Invalid value");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
