package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.PaymentRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.BankPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.CardPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.UpiPayment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodFactoryTest {

    @Test
    void shouldCreateUpiPayment() {

        PaymentRequest request =
                new PaymentRequest();

        request.setPaymentType("UPI");
        request.setUpiId("bharath@upi");

        Payable payable =
                PaymentMethodFactory.create(
                        request
                );

        assertInstanceOf(
                UpiPayment.class,
                payable
        );
    }

    @Test
    void shouldCreateCardPayment() {

        PaymentRequest request =
                new PaymentRequest();

        request.setPaymentType("CARD");
        request.setCardNumber(
                "4111111111111111"
        );

        request.setCardHolderName(
                "Bharath"
        );

        Payable payable =
                PaymentMethodFactory.create(
                        request
                );

        assertInstanceOf(
                CardPayment.class,
                payable
        );
    }

    @Test
    void shouldCreateBankPayment() {

        PaymentRequest request =
                new PaymentRequest();

        request.setPaymentType("BANK");
        request.setBankName("SBI");
        request.setAccountNumber(
                "123456789012"
        );

        Payable payable =
                PaymentMethodFactory.create(
                        request
                );

        assertInstanceOf(
                BankPayment.class,
                payable
        );
    }
}
