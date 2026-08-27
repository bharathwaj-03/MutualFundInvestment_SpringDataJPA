package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

/**
 * Data transfer object used to return holding information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class HoldingResponse {

    /**
     * Unique identifier of the holding being redeemed.
     */
    private String holdingId;

    /**
     * Unique identifier of the investor portfolio.
     */
    private String portfolioId;

    /**
     * Unique identifier of the mutual fund.
     */
    private String fundId;

    /**
     * Display name of the mutual fund.
     */
    private String fundName;

    /**
     * Category to which the mutual fund belongs.
     */
    private String fundCategory;

    /**
     * Total mutual fund units currently held.
     */
    private double unitsOwned;

    /**
     * Total amount invested in the holding.
     */
    private double investedAmount;

    /**
     * Average NAV at which the units were acquired.
     */
    private double averageNav;

    /**
     * Current NAV used to value the holding.
     */
    private double currentNav;

    /**
     * Current market value calculated for the investment or portfolio.
     */
    private double currentValue;

    /**
     * Difference between current value and invested amount.
     */
    private double profitOrLoss;


    /**
     * Creates a HoldingResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public HoldingResponse() {
    }


    /**
     * Returns the holding id.
     * @return unique identifier of the holding being redeemed.
     */
    public String getHoldingId() {
        return holdingId;
    }

    /**
     * Updates the holding id carried by this DTO.
     * @param holdingId unique identifier of the holding being redeemed.
     */
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
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
     * Returns the fund id.
     * @return unique identifier of the mutual fund.
     */
    public String getFundId() {
        return fundId;
    }

    /**
     * Updates the fund id carried by this DTO.
     * @param fundId unique identifier of the mutual fund.
     */
    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    /**
     * Returns the fund name.
     * @return display name of the mutual fund.
     */
    public String getFundName() {
        return fundName;
    }

    /**
     * Updates the fund name carried by this DTO.
     * @param fundName display name of the mutual fund.
     */
    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    /**
     * Returns the fund category.
     * @return category to which the mutual fund belongs.
     */
    public String getFundCategory() {
        return fundCategory;
    }

    /**
     * Updates the fund category carried by this DTO.
     * @param fundCategory category to which the mutual fund belongs.
     */
    public void setFundCategory(String fundCategory) {
        this.fundCategory = fundCategory;
    }

    /**
     * Returns the units owned.
     * @return total mutual fund units currently held.
     */
    public double getUnitsOwned() {
        return unitsOwned;
    }

    /**
     * Updates the units owned carried by this DTO.
     * @param unitsOwned total mutual fund units currently held.
     */
    public void setUnitsOwned(double unitsOwned) {
        this.unitsOwned = unitsOwned;
    }

    /**
     * Returns the invested amount.
     * @return total amount invested in the holding.
     */
    public double getInvestedAmount() {
        return investedAmount;
    }

    /**
     * Updates the invested amount carried by this DTO.
     * @param investedAmount total amount invested in the holding.
     */
    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * Returns the average nav.
     * @return average NAV at which the units were acquired.
     */
    public double getAverageNav() {
        return averageNav;
    }

    /**
     * Updates the average nav carried by this DTO.
     * @param averageNav average NAV at which the units were acquired.
     */
    public void setAverageNav(double averageNav) {
        this.averageNav = averageNav;
    }

    /**
     * Returns the current nav.
     * @return current NAV used to value the holding.
     */
    public double getCurrentNav() {
        return currentNav;
    }

    /**
     * Updates the current nav carried by this DTO.
     * @param currentNav current NAV used to value the holding.
     */
    public void setCurrentNav(double currentNav) {
        this.currentNav = currentNav;
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
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Returns the profit or loss.
     * @return difference between current value and invested amount.
     */
    public double getProfitOrLoss() {
        return profitOrLoss;
    }

    /**
     * Updates the profit or loss carried by this DTO.
     * @param profitOrLoss difference between current value and invested amount.
     */
    public void setProfitOrLoss(double profitOrLoss) {
        this.profitOrLoss = profitOrLoss;
    }
}