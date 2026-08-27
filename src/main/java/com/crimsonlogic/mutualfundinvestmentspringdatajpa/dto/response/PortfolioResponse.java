package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDate;

/**
 * Data transfer object used to return portfolio information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class PortfolioResponse {

    /**
     * Unique identifier of the investor portfolio.
     */
    private String portfolioId;
    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;
    /**
     * Name of the investor associated with the response.
     */
    private String investorName;
    /**
     * Date of the most recent activity in the portfolio.
     */
    private LocalDate lastActivityDate;
    /**
     * Current market value calculated for the investment or portfolio.
     */
    private double currentValue;

    /**
     * Creates a PortfolioResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public PortfolioResponse() {
    }

    /**
     * Returns the portfolio id.
     * @return unique identifier of the investor portfolio.
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Updates the portfolio id carried by this DTO.
     * @param portfolioId unique identifier of the investor portfolio.
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * Returns the investor id.
     * @return unique identifier of the investor associated with the request or response.
     */
    public String getInvestorId() {
        return investorId;
    }

    /**
     * Updates the investor id carried by this DTO.
     * @param investorId unique identifier of the investor associated with the request or response.
     */
    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    /**
     * Returns the investor name.
     * @return name of the investor associated with the response.
     */
    public String getInvestorName() {
        return investorName;
    }

    /**
     * Updates the investor name carried by this DTO.
     * @param investorName name of the investor associated with the response.
     */
    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    /**
     * Returns the last activity date.
     * @return date of the most recent activity in the portfolio.
     */
    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     * Updates the last activity date carried by this DTO.
     * @param lastActivityDate date of the most recent activity in the portfolio.
     */
    public void setLastActivityDate(
            LocalDate lastActivityDate) {

        this.lastActivityDate =
                lastActivityDate;
    }

    /**
     * Returns the current value.
     * @return current market value calculated for the investment or portfolio.
     */
    public double getCurrentValue() {
        return currentValue;
    }

    /**
     * Updates the current value carried by this DTO.
     * @param currentValue current market value calculated for the investment or portfolio.
     */
    public void setCurrentValue(
            double currentValue) {

        this.currentValue =
                currentValue;
    }
}