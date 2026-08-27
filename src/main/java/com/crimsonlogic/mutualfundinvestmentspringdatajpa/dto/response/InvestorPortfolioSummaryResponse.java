package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.util.Map;

/**
 * Data transfer object used to return investor portfolio summary information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class InvestorPortfolioSummaryResponse {

    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;

    /**
     * Name of the investor associated with the response.
     */
    private String investorName;

    /**
     * Total current value of the investor portfolio.
     */
    private double totalPortfolioValue;

    /**
     * Portfolio values grouped by mutual fund category.
     */
    private Map<String, Double>
            categoryValues;


    /**
     * Creates a InvestorPortfolioSummaryResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public InvestorPortfolioSummaryResponse() {
    }


    /**
     * Creates a InvestorPortfolioSummaryResponse object with the supplied data.
     * @param investorId Unique identifier of the investor associated with the request or response.
     * @param investorName Name of the investor associated with the response.
     * @param totalPortfolioValue Total current value of the investor portfolio.
     * @param Map<String map< string
     * @param categoryValues Portfolio values grouped by mutual fund category.
     */
    public InvestorPortfolioSummaryResponse(
            String investorId,
            String investorName,
            double totalPortfolioValue,
            Map<String, Double> categoryValues) {

        this.investorId =
                investorId;

        this.investorName =
                investorName;

        this.totalPortfolioValue =
                totalPortfolioValue;

        this.categoryValues =
                categoryValues;
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
    public void setInvestorId(
            String investorId) {
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
    public void setInvestorName(
            String investorName) {
        this.investorName = investorName;
    }

    /**
     * Returns the total portfolio value.
     * @return total current value of the investor portfolio.
     */
    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    /**
     * Updates the total portfolio value carried by this DTO.
     * @param totalPortfolioValue total current value of the investor portfolio.
     */
    public void setTotalPortfolioValue(
            double totalPortfolioValue) {
        this.totalPortfolioValue =
                totalPortfolioValue;
    }

    /**
     * Returns the category values.
     * @return portfolio values grouped by mutual fund category.
     */
    public Map<String, Double>
    getCategoryValues() {
        return categoryValues;
    }

    /**
     * Updates the category values carried by this DTO.
     * @param categoryValues portfolio values grouped by mutual fund category.
     */
    public void setCategoryValues(
            Map<String, Double> categoryValues) {
        this.categoryValues =
                categoryValues;
    }
}