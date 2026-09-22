package com.ibm.digicusthub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExistException(UserExistsException ex){
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status",HttpStatus.CONFLICT.value());
        body.put("error","User Already Exists with given data");
        body.put("message",ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


}
