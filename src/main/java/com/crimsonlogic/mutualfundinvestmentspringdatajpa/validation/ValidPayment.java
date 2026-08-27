package com.crimsonlogic.mutualfundinvestmentspringdatajpa.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PaymentRequestValidator.class)
public @interface ValidPayment {

    String message()
            default "Invalid payment details.";

    Class<?>[] groups()
            default {};

    Class<? extends Payload>[] payload()
            default {};
}