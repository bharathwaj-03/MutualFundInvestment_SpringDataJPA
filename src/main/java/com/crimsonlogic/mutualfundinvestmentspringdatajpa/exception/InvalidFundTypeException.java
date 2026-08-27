package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;

public class InvalidFundTypeException extends RuntimeException {

    public InvalidFundTypeException(String message) {
        super(message);
    }

    public InvalidFundTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
