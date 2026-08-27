package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.PaymentRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.BankPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.CardPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.UpiPayment;

/**
 * Factory that creates the required payment strategy from an incoming payment request.
 */
final class PaymentMethodFactory {

    /**
     * Prevents instantiation because this class provides only factory behavior.
     */
    private PaymentMethodFactory() {
    }

    /**
     * Creates the payment implementation that matches the payment type supplied in the request.
     *
     * @param request payment details received from the API request
     * @return payment strategy matching the requested payment type
     */
    static Payable create(
            PaymentRequest request) {

        if (request == null ||
                request.getPaymentType() == null ||
                request.getPaymentType()
                        .trim()
                        .isEmpty()) {

            throw new IllegalArgumentException(
                    "Payment type is required."
            );
        }


        String type =
                request.getPaymentType()
                        .trim()
                        .toUpperCase();


        switch (type) {

            case "UPI":

                UpiPayment upi =
                        new UpiPayment();

                try {

                    upi.setUpiId(
                            request.getUpiId()
                    );

                } catch (Exception e) {

                    throw new IllegalArgumentException(
                            e.getMessage(),
                            e
                    );
                }

                return upi;


            case "CARD":

                CardPayment card =
                        new CardPayment();

                try {

                    card.setCardNumber(
                            request.getCardNumber()
                    );

                } catch (Exception e) {

                    throw new IllegalArgumentException(
                            e.getMessage(),
                            e
                    );
                }


                card.setCardHolderName(
                        request.getCardHolderName()
                );

                return card;


            case "BANK":

                BankPayment bank =
                        new BankPayment();

                bank.setBankName(
                        request.getBankName()
                );


                try {

                    bank.setAccountNumber(
                            request.getAccountNumber()
                    );

                } catch (Exception e) {

                    throw new IllegalArgumentException(
                            e.getMessage(),
                            e
                    );
                }


                return bank;


            default:

                throw new IllegalArgumentException(
                        "Unsupported payment type: "
                                + request.getPaymentType()
                );
        }
    }
}
