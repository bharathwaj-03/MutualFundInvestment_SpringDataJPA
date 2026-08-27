package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;

/**
 * Defines business operations for investor portfolios and portfolio valuation.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_PortfolioService {

    /**
     * Creates a portfolio for the specified investor when one does not already exist.
     *
     * @param userId user identifier
     * @return result of the business operation
     */

    Portfolio createPortfolio(String userId);

    /**
     * Retrieves the portfolio associated with the specified investor.
     *
     * @param investorId investor identifier
     * @return result of the business operation
     */

    Portfolio getPortfolio(String investorId);

    /**
     * Updates the portfolio maintenance date after portfolio-related activity.
     *
     * @param portfolio portfolio information
     */

    void updatePortfolioDate(Portfolio portfolio);

    /**
     * Calculates the current total value of an investor portfolio from its holdings.
     *
     * @param investorId investor identifier
     * @return calculated numeric value
     */

    double calculatePortfolioValue(String investorId);
}