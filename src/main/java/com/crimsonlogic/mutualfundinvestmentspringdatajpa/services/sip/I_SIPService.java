package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.sip;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Defines business operations for SIP validation, creation, retrieval, update, and cancellation.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_SIPService {

    /**
     * Validates SIP input and returns field-specific validation messages without creating the SIP.
     *
     * @param fundId mutual fund identifier
     * @param monthlyAmount monthly SIP installment amount
     * @param investmentYears investment duration in years
     * @param startDate requested SIP start date
     * @param paymentType payment method type
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */

    Map<String, String> validateSIP(
            String fundId,
            double monthlyAmount,
            int investmentYears,
            LocalDate startDate,
            String paymentType
    );

    /**
     * Creates a SIP after validating investor, fund, payment, amount, start date, and investment period, and records the first installment effects.
     *
     * @param userId user identifier
     * @param fundId mutual fund identifier
     * @param monthlyAmount monthly SIP installment amount
     * @param startDate requested SIP start date
     * @param investmentYears investment duration in years
     * @param paymentMethod payment strategy used to execute the payment
     * @return result of the business operation
     */

    SIP startSIP(
            String userId,
            String fundId,
            double monthlyAmount,
            LocalDate startDate,
            int investmentYears,
            Payable paymentMethod
    );

    /**
     * Retrieves a SIP by its unique SIP ID.
     *
     * @param sipId SIP identifier
     * @return result of the business operation
     */

    SIP getSIPById(
            String sipId
    );

    /**
     * Retrieves all SIP records belonging to the specified investor.
     *
     * @param userId user identifier
     * @return list of matching records or response objects
     */

    List<SIP> getSIPsByUser(
            String userId
    );

    /**
     * Retrieves all SIP records.
     *
     * @return list of matching records or response objects
     */

    List<SIP> getAllSIPs();

    /**
     * Persists changes made to an existing SIP.
     *
     * @param sip SIP information
     * @return true when the operation succeeds; otherwise false
     */

    boolean updateSIP(
            SIP sip
    );

    /**
     * Cancels the SIP identified by the supplied SIP ID.
     *
     * @param sipId SIP identifier
     * @return true when the operation succeeds; otherwise false
     */

    boolean cancelSIP(
            String sipId
    );
}