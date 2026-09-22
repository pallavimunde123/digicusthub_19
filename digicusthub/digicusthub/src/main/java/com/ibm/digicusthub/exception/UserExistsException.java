package com.ibm.digicusthub.exception;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String message){
        super(message);
    }
}
