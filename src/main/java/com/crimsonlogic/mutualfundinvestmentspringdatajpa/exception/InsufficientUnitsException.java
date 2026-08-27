package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;

public class InsufficientUnitsException extends RuntimeException {

    public InsufficientUnitsException(String message) {
        super(message);
    }

    public InsufficientUnitsException(String message, Throwable cause) {
        super(message, cause);
    }
}
