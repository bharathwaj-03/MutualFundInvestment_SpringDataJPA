package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.PaymentFailedException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.UpiPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private InvestorRepository investorRepository;

    private AutoCloseable mocks;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        paymentService =
                new PaymentService(paymentRepository, investorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void shouldProcessSuccessfulPayment() {
        Payable payable = mock(Payable.class);

        assertTrue(paymentService.processPayment(payable, 1000));

        verify(payable).processPayment(1000);
    }

    @Test
    void shouldThrowWhenPaymentProcessingFails() {
        Payable payable = mock(Payable.class);
        doThrow(new RuntimeException("failed"))
                .when(payable)
                .processPayment(1000);

        assertThrows(
                PaymentFailedException.class,
                () -> paymentService.processPayment(payable, 1000)
        );
    }

    @Test
    void shouldRejectInvalidPaymentAmount() {
        assertFalse(paymentService.validatePayment(0));
        assertFalse(paymentService.validatePayment(-1));
        assertTrue(paymentService.validatePayment(100));
    }

    @Test
    void shouldSaveUpiPayment() throws UserDataValidationException {
        Investor investor = new Investor();
        investor.setUserId("INV001");

        UpiPayment upi = new UpiPayment();
        upi.setUpiId("bharath@upi");

        when(investorRepository.findById("INV001"))
                .thenReturn(Optional.of(investor));

        Payment result =
                paymentService.savePayment(
                        "INV001",
                        upi,
                        5000
                );

        assertEquals("UPI", result.getPaymentMethod());
        assertSame(investor, result.getInvestor());
        assertEquals("SUCCESS", result.getPaymentStatus());

        verify(paymentRepository).save(result);
    }

    @Test
    void shouldThrowWhenInvestorMissingWhileSavingPayment() throws UserDataValidationException {
        when(investorRepository.findById("INV404"))
                .thenReturn(Optional.empty());

        UpiPayment upi = new UpiPayment();
        upi.setUpiId("test@upi");

        assertThrows(
                ResourceNotFoundException.class,
                () -> paymentService.savePayment(
                        "INV404",
                        upi,
                        1000
                )
        );
    }

    @Test
    void shouldGetPaymentById() {
        Payment payment = new Payment();

        when(paymentRepository.findById("PAY001"))
                .thenReturn(Optional.of(payment));

        assertSame(payment, paymentService.getPaymentById("PAY001"));
    }
}
