package com.crimsonlogic.mutualfundinvestmentspringdatajpa.validation;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.PaymentRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentRequestValidator
        implements ConstraintValidator<
        ValidPayment,
        PaymentRequest> {

    @Override
    public boolean isValid(
            PaymentRequest payment,
            ConstraintValidatorContext context) {

        if (payment == null) {
            return true;
        }

        if (payment.getPaymentType() == null ||
                payment.getPaymentType()
                        .trim()
                        .isEmpty()) {

            return true;
        }


        String paymentType =
                payment.getPaymentType()
                        .trim()
                        .toUpperCase();


        context.disableDefaultConstraintViolation();


        switch (paymentType) {

            case "UPI":

                if (isBlank(
                        payment.getUpiId())) {

                    addError(
                            context,
                            "upiId",
                            "UPI ID is required for UPI payment."
                    );

                    return false;
                }

                return true;


            case "CARD":

                boolean cardValid = true;

                if (isBlank(
                        payment.getCardNumber())) {

                    addError(
                            context,
                            "cardNumber",
                            "Card number is required for card payment."
                    );

                    cardValid = false;
                }


                if (isBlank(
                        payment.getCardHolderName())) {

                    addError(
                            context,
                            "cardHolderName",
                            "Card holder name is required for card payment."
                    );

                    cardValid = false;
                }

                return cardValid;


            case "BANK":

                boolean bankValid = true;

                if (isBlank(
                        payment.getBankName())) {

                    addError(
                            context,
                            "bankName",
                            "Bank name is required for bank payment."
                    );

                    bankValid = false;
                }


                if (isBlank(
                        payment.getAccountNumber())) {

                    addError(
                            context,
                            "accountNumber",
                            "Account number is required for bank payment."
                    );

                    bankValid = false;
                }

                return bankValid;


            default:

                // @Pattern on paymentType will handle
                // unsupported payment types.
                return true;
        }
    }


    private boolean isBlank(
            String value) {

        return value == null ||
                value.trim().isEmpty();
    }


    private void addError(
            ConstraintValidatorContext context,
            String field,
            String message) {

        context
                .buildConstraintViolationWithTemplate(
                        message
                )
                .addPropertyNode(
                        field
                )
                .addConstraintViolation();
    }
}