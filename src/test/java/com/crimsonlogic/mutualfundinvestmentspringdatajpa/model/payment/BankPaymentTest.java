package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankPaymentTest {

    @Test
    void shouldStoreBankPaymentDetails()
            throws Exception {

        BankPayment p =
                new BankPayment();

        p.setBankName(
                "SBI"
        );

        p.setAccountNumber(
                "123456789"
        );


        assertEquals(
                "SBI",
                p.getBankName()
        );

        assertEquals(
                "123456789",
                p.getAccountNumber()
        );

        assertDoesNotThrow(
                () ->
                        p.processPayment(
                                1000
                        )
        );
    }


    @Test
    void shouldRejectInvalidAccountNumber() {

        BankPayment p =
                new BankPayment();

        assertThrows(
                UserDataValidationException.class,
                () ->
                        p.setAccountNumber(
                                "12345"
                        )
        );
    }


    @Test
    void equalPaymentsShouldMatch()
            throws Exception {

        BankPayment a =
                new BankPayment();

        BankPayment b =
                new BankPayment();


        a.setBankName(
                "SBI"
        );

        b.setBankName(
                "SBI"
        );


        a.setAccountNumber(
                "123456789"
        );

        b.setAccountNumber(
                "123456789"
        );


        assertEquals(
                a,
                b
        );

        assertEquals(
                a.hashCode(),
                b.hashCode()
        );
    }
}