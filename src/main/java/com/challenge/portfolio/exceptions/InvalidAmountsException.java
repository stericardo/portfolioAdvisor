package com.challenge.portfolio.exceptions;

public class InvalidAmountsException extends RuntimeException {

    public InvalidAmountsException(String message) {
        super(message + " -- InvalidAmountsException");
    }

}
