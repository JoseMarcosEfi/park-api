package com.jmarcos.demoparkapi.exception;

public class UserNameUniqueViolationException extends RuntimeException{
    public UserNameUniqueViolationException(String message) {
        super(message);
    }
}
