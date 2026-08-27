package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for payment persistence and payment-history lookups.
 *
 * Spring Data derives filtering queries from the repository method names.
 */
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * Finds all payments recorded for a specific investor.
     *
     * @param userId investor user ID
     * @return payments belonging to the investor
     */
    List<Payment> findByInvestor_UserId(String userId);

    /**
     * Finds payments by their processing status.
     *
     * @param paymentStatus payment status to filter by
     * @return payments matching the supplied status
     */
    List<Payment> findByPaymentStatus(String paymentStatus);
}
