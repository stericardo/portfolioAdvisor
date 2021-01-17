package com.challenge.portfolio.exceptions;

public class RiskNotFoundException extends RuntimeException {

    public RiskNotFoundException(String message) {
        super(message + " -- RiskNotFoundException");
    }

}
