package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.FundCategoryPerformanceResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorPortfolioSummaryResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;

import java.util.List;

/**
 * Defines business operations for managing and analyzing portfolio holdings.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_HoldingService {

    /**
     * Creates a holding and generates a holding ID when one has not already been assigned.
     *
     * @param holding holding information
     * @return true when the operation succeeds; otherwise false
     */

    boolean createHolding(Holding holding);

    /**
     * Retrieves a holding together with the related portfolio and mutual fund information.
     *
     * @param holdingId holding identifier
     * @return result of the business operation
     */

    Holding getHoldingById(String holdingId);

    /**
     * Retrieves all holdings belonging to the specified portfolio.
     *
     * @param portfolioId portfolio identifier
     * @return list of matching records or response objects
     */

    List<Holding> getHoldingsByPortfolio(String portfolioId);

    /**
     * Finds the holding for a specific portfolio and mutual fund combination.
     *
     * @param portfolioId portfolio identifier
     * @param fundId mutual fund identifier
     * @return result of the business operation
     */

    Holding getHoldingByPortfolioAndFund(
            String portfolioId,
            String fundId
    );

    /**
     * Persists changes made to an existing holding.
     *
     * @param holding holding information
     * @return true when the operation succeeds; otherwise false
     */

    boolean updateHolding(Holding holding);

    /**
     * Deletes a holding when the specified holding ID exists.
     *
     * @param holdingId holding identifier
     * @return true when the operation succeeds; otherwise false
     */

    boolean deleteHolding(String holdingId);

    /**
     * Retrieves all holdings available in the system.
     *
     * @return list of matching records or response objects
     */

    List<Holding> getAllHoldings();
    /**
     * Retrieves holdings owned by the specified investor.
     *
     * @param investorId investor identifier
     * @return list of matching records or response objects
     */
    List<Holding> getHoldingsByInvestor(
            String investorId
    );

    /**
     * Calculates category-level invested value, current value, profit or loss, and return information from holding data.
     *
     * @return list of matching records or response objects
     */

    List<FundCategoryPerformanceResponse>
    getFundCategoryPerformance();

    /**
     * Builds investor portfolio summaries grouped by mutual fund category and filtered by investor active status.
     *
     * @param active investor active-status filter
     * @return list of matching records or response objects
     */

    List<InvestorPortfolioSummaryResponse>
    getAllInvestorPortfolioSummaries(
            boolean active
    );
}