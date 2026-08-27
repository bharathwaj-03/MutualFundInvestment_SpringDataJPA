package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;

public class DuplicateResourceException extends Exception {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
