package com.ebanking.atmservice.exceptions;

public class AtmNotFoundException extends Exception {
    public AtmNotFoundException(String atmNotFound) {
        super(atmNotFound);
    }
}
