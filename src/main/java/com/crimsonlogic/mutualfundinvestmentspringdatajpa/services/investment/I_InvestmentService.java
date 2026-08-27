package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;

import java.util.List;
import java.util.Map;

/**
 * Defines business operations for validating, creating, and retrieving lump-sum investments.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_InvestmentService {

    /**
     * Executes the complete lump-sum investment workflow after validating investor, fund, amount, payment, and investment period.
     *
     * @param investorId investor identifier
     * @param fundId mutual fund identifier
     * @param amount monetary amount for the operation
     * @param investmentYears investment duration in years
     * @param paymentMethod payment strategy used to execute the payment
     * @return result of the business operation
     */

    Investment startInvestment(
            String investorId,
            String fundId,
            double amount,
            int investmentYears,
            Payable paymentMethod
    );

    /**
     * Retrieves an investment by its unique investment ID.
     *
     * @param investmentId investment identifier
     * @return result of the business operation
     */

    Investment getInvestmentById(
            String investmentId
    );

    /**
     * Retrieves all investments associated with the specified investor.
     *
     * @param userId user identifier
     * @return list of matching records or response objects
     */

    List<Investment> getInvestmentsByUser(
            String userId
    );

    /**
     * Retrieves all investment records.
     *
     * @return list of matching records or response objects
     */

    List<Investment> getAllInvestments();
    /**
     * Validates investment input and returns field-specific validation messages without starting an investment.
     *
     * @param fundId mutual fund identifier
     * @param amount monetary amount for the operation
     * @param investmentYears investment duration in years
     * @param paymentType payment method type
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */
    Map<String, String> validateInvestment(
            String fundId,
            double amount,
            int investmentYears,
            String paymentType
    );
}