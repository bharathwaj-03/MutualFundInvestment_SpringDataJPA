package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardPaymentTest {

    @Test
    void shouldStoreCardPaymentDetails()
            throws Exception {

        CardPayment p =
                new CardPayment();

        p.setCardNumber(
                "4111111111111111"
        );

        p.setCardHolderName(
                "BHARATH"
        );


        assertEquals(
                "4111111111111111",
                p.getCardNumber()
        );

        assertEquals(
                "BHARATH",
                p.getCardHolderName()
        );

        assertDoesNotThrow(
                () ->
                        p.processPayment(
                                1000
                        )
        );
    }


    @Test
    void shouldRejectInvalidCardNumber() {

        CardPayment p =
                new CardPayment();

        assertThrows(
                UserDataValidationException.class,
                () ->
                        p.setCardNumber(
                                "123456"
                        )
        );
    }
}