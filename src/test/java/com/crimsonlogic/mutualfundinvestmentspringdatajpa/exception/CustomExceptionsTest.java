package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionsTest {

    @Test
    void shouldPreserveMessageForResourceNotFoundException() {
        ResourceNotFoundException exception =
                new ResourceNotFoundException(
                        "Resource missing"
                );

        assertEquals(
                "Resource missing",
                exception.getMessage()
        );
    }

    @Test
    void shouldPreserveCauseForResourceNotFoundException() {
        RuntimeException cause =
                new RuntimeException("DB error");

        ResourceNotFoundException exception =
                new ResourceNotFoundException(
                        "Resource missing",
                        cause
                );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForInvalidRequestException() {
        RuntimeException cause =
                new RuntimeException("cause");

        InvalidRequestException exception =
                new InvalidRequestException(
                        "Invalid request",
                        cause
                );

        assertEquals(
                "Invalid request",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForDuplicateResourceException() {
        RuntimeException cause =
                new RuntimeException("cause");

        DuplicateResourceException exception =
                new DuplicateResourceException(
                        "Duplicate resource",
                        cause
                );

        assertEquals(
                "Duplicate resource",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForAuthenticationException() {
        RuntimeException cause =
                new RuntimeException("cause");

        AuthenticationException exception =
                new AuthenticationException(
                        "Authentication failed",
                        cause
                );

        assertEquals(
                "Authentication failed",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForPaymentFailedException() {
        RuntimeException cause =
                new RuntimeException("cause");

        PaymentFailedException exception =
                new PaymentFailedException(
                        "Payment failed",
                        cause
                );

        assertEquals(
                "Payment failed",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForInsufficientUnitsException() {
        RuntimeException cause =
                new RuntimeException("cause");

        InsufficientUnitsException exception =
                new InsufficientUnitsException(
                        "Insufficient units",
                        cause
                );

        assertEquals(
                "Insufficient units",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForInvalidUnitsException() {
        RuntimeException cause =
                new RuntimeException("cause");

        InvalidUnitsException exception =
                new InvalidUnitsException(
                        "Invalid units",
                        cause
                );

        assertEquals(
                "Invalid units",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessageAndCauseForInvalidFundTypeException() {
        RuntimeException cause =
                new RuntimeException("cause");

        InvalidFundTypeException exception =
                new InvalidFundTypeException(
                        "Invalid fund type",
                        cause
                );

        assertEquals(
                "Invalid fund type",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void shouldPreserveMessagesForCheckedExceptions() {

        assertEquals(
                "Redemption missing",
                new RedemptionNotFoundException(
                        "Redemption missing"
                ).getMessage()
        );

        assertEquals(
                "Investment invalid",
                new InvalidInvestmentException(
                        "Investment invalid"
                ).getMessage()
        );

        assertEquals(
                "Portfolio missing",
                new PortfolioNotFoundException(
                        "Portfolio missing"
                ).getMessage()
        );

        assertEquals(
                "Investor missing",
                new InvestorNotFoundException(
                        "Investor missing"
                ).getMessage()
        );

        assertEquals(
                "Date invalid",
                new InvalidDateException(
                        "Date invalid"
                ).getMessage()
        );

        assertEquals(
                "User data invalid",
                new UserDataValidationException(
                        "User data invalid"
                ).getMessage()
        );

        assertEquals(
                "Amount invalid",
                new InvalidAmountException(
                        "Amount invalid"
                ).getMessage()
        );

        assertEquals(
                "Transaction missing",
                new TransactionNotFoundException(
                        "Transaction missing"
                ).getMessage()
        );
    }

    @Test
    void shouldPreserveMessageForMutualFundNotFoundException() {

        MutualFundNotFoundException exception =
                new MutualFundNotFoundException(
                        "Fund missing"
                );

        assertEquals(
                "Fund missing",
                exception.getMessage()
        );
    }
}
