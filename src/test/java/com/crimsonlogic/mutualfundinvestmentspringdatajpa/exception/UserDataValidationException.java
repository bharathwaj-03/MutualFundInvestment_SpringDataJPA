package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;


//validate all the user entered data includes name,phoneNum,email,pancard,upiID etc.

public class UserDataValidationException extends Exception{
    public UserDataValidationException(String message) {
        super(message);
    }
}
