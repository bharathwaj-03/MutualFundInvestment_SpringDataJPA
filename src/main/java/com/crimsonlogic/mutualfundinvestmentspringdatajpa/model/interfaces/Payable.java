package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces;

/**
 * Defines the payment-processing contract implemented by supported payment methods.
 */
@FunctionalInterface
public interface Payable {

    /**
     * Processes a payment using the concrete payment implementation.
     *
     * @param amount amount to be processed
     */
    void processPayment(double amount);
}
