package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InactiveInvestorResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;

import java.util.List;
import java.util.Map;

/**
 * Defines investor registration, authentication, profile, validation, and account-status operations.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_InvestorService {

    /**
     * Validates investor and nominee information and returns all field-specific validation errors found.
     *
     * @param investor investor information
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */

    Map<String, String> validateInvestor(
            Investor investor
    );

    /**
     * Registers a new investor after validation, security processing, nominee persistence, and portfolio creation.
     *
     * @param investor investor information
     * @return true when the operation succeeds; otherwise false
     */

    boolean registerInvestor(
            Investor investor
    );

    /**
     * Authenticates an active investor using the supplied investor ID and password.
     *
     * @param userId user identifier
     * @param password plain password supplied for verification
     * @return result of the business operation
     */

    Investor authenticateInvestor(
            String userId,
            String password
    );
   /**
    * Retrieves an investor by user ID.
    *
    * @param userId user identifier
    * @return result of the business operation
    */
   Investor getInvestorByUserId(  String userId);

    /**
     * Updates editable investor and nominee profile information while preserving protected credentials.
     *
     * @param investor investor information
     * @return true when the operation succeeds; otherwise false
     */

    boolean updateInvestorProfile(
            Investor investor
    );

    /**
     * Soft-deactivates an active investor account without deleting its stored data.
     *
     * @param investorId investor identifier
     * @return true when the operation succeeds; otherwise false
     */

    boolean deactivateInvestor(
            String investorId);

    /**
     * Retrieves inactive investors and converts them to response objects suitable for administration views.
     *
     * @return list of matching records or response objects
     */

    List<InactiveInvestorResponse>
    getInactiveInvestors();

    /**
     * Reactivates an investor account that is currently inactive.
     *
     * @param investorId investor identifier
     * @return true when the operation succeeds; otherwise false
     */

    boolean activateInvestor(
            String investorId
    );

    /**
     * Builds an investor profile response with nominee details and decrypted sensitive values while excluding the password.
     *
     * @param investorId investor identifier
     * @return result of the business operation
     */

    InvestorProfileResponse
    getInvestorProfile(
            String investorId
    );
}