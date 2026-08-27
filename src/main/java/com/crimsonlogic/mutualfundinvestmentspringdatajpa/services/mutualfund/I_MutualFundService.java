package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;

import java.util.List;

/**
 * Defines business operations for mutual fund maintenance, retrieval, filtering, and NAV management.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_MutualFundService {

    /**
     * Persists a newly created mutual fund.
     *
     * @param fund mutual fund information
     */

    void addFund(MutualFund fund);

    /**
     * Persists changes made to an existing mutual fund.
     *
     * @param fund mutual fund information
     */

    void updateFund(MutualFund fund);

    /**
     * Deletes the mutual fund identified by the supplied fund ID.
     *
     * @param fundId mutual fund identifier
     */

    void deleteFund(String fundId);

    /**
     * Retrieves a mutual fund by its unique fund ID.
     *
     * @param fundId mutual fund identifier
     * @return result of the business operation
     */

    MutualFund getFundById(String fundId);

    /**
     * Retrieves a mutual fund by its fund name.
     *
     * @param fundName mutual fund name
     * @return result of the business operation
     */

    MutualFund getFundByName(String fundName);

    /**
     * Retrieves mutual funds belonging to the supplied fund category.
     *
     * @param category fund category used for filtering
     * @return list of matching records or response objects
     */

    List<MutualFund> getFundsByCategory(String category);

    /**
     * Retrieves all mutual funds.
     *
     * @return list of matching records or response objects
     */

    List<MutualFund> getAllFunds();

    /**
     * Updates the current NAV of a fund and records the NAV change for audit/history purposes.
     *
     * @param fundId mutual fund identifier
     * @param newNAV new NAV value to apply
     * @param adminId administrator identifier
     */

    void updateNAV(
            String fundId,
            double newNAV,
            String adminId
    );

    /**
     * Returns the current NAV of the specified mutual fund.
     *
     * @param fundId mutual fund identifier
     * @return calculated numeric value
     */

    double getCurrentNav(String fundId);
}