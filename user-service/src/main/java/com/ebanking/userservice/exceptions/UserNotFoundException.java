package com.ebanking.userservice.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String string) {
        super(string);
    }
}
