package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;

/**
 * Defines payment processing, validation, persistence, retrieval, and receipt operations.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_PaymentService {

    /**
     * Validates the amount and delegates payment execution to the supplied payment method.
     *
     * @param paymentMethod payment strategy used to execute the payment
     * @param amount monetary amount for the operation
     * @return true when the operation succeeds; otherwise false
     */

    boolean processPayment(
            Payable paymentMethod,
            double amount
    );

    /**
     * Creates and persists a payment record for an investor after a successful payment operation.
     *
     * @param investorId investor identifier
     * @param paymentMethod payment strategy used to execute the payment
     * @param amount monetary amount for the operation
     * @return result of the business operation
     */

    Payment savePayment(
            String investorId,
            Payable paymentMethod,
            double amount
    );

    /**
     * Retrieves a payment by its unique payment ID.
     *
     * @param paymentId payment identifier
     * @return result of the business operation
     */

    Payment getPaymentById(
            String paymentId
    );

    /**
     * Checks whether a payment amount satisfies the service payment rules.
     *
     * @param amount monetary amount for the operation
     * @return true when the operation succeeds; otherwise false
     */

    boolean validatePayment(
            double amount
    );

    /**
     * Generates receipt output for a completed payment.
     *
     * @param amount monetary amount for the operation
     * @param paymentType payment method type
     */

    void generateReceipt(
            double amount,
            String paymentType
    );
}