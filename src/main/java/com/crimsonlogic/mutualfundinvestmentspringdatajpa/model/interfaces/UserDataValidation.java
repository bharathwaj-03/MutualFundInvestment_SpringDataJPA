package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;

/**
 * Functional interface used to validate and normalize user-supplied data.
 */
@FunctionalInterface
public interface UserDataValidation {

    /**
     * Validates the supplied user input and returns the accepted or normalized value.
     *
     * @param str user-supplied value to validate
     * @return validated or normalized value
     * @throws UserDataValidationException when the supplied value violates a validation rule
     */
    String validate(String str) throws UserDataValidationException;
}
