package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

/**
 * Data transfer object used to return fund category performance information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class FundCategoryPerformanceResponse {

    /**
     * Category to which the mutual fund belongs.
     */
    private String fundCategory;

    /**
     * Total amount invested across the fund category.
     */
    private double totalInvestedAmount;

    /**
     * Current market value calculated for the investment or portfolio.
     */
    private double currentValue;

    /**
     * Difference between current value and invested amount.
     */
    private double profitOrLoss;

    /**
     * Percentage return calculated from profit or loss against invested amount.
     */
    private double returnPercentage;


    /**
     * Creates a FundCategoryPerformanceResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public FundCategoryPerformanceResponse() {
    }


    /**
     * Creates a FundCategoryPerformanceResponse object with the supplied data.
     * @param fundCategory Category to which the mutual fund belongs.
     * @param totalInvestedAmount Total amount invested across the fund category.
     * @param currentValue Current market value calculated for the investment or portfolio.
     */
    public FundCategoryPerformanceResponse(
            String fundCategory,
            double totalInvestedAmount,
            double currentValue) {

        this.fundCategory =
                fundCategory;

        this.totalInvestedAmount =
                totalInvestedAmount;

        this.currentValue =
                currentValue;

        // Profit or loss is derived from the difference between current value and invested amount.
        this.profitOrLoss =
                currentValue
                        - totalInvestedAmount;

        // Return percentage is calculated only when an invested amount exists, preventing division by zero.
        if (totalInvestedAmount > 0) {

            this.returnPercentage =
                    Math.round(
                            ((profitOrLoss
                                    / totalInvestedAmount)
                                    * 100)
                                    * 100.0
                    ) / 100.0;
        }
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
    public void setFundCategory(
            String fundCategory) {

        this.fundCategory =
                fundCategory;
    }


    /**
     * Returns the total invested amount.
     * @return total amount invested across the fund category.
     */
    public double getTotalInvestedAmount() {

        return totalInvestedAmount;
    }


    /**
     * Updates the total invested amount carried by this DTO.
     * @param totalInvestedAmount total amount invested across the fund category.
     */
    public void setTotalInvestedAmount(
            double totalInvestedAmount) {

        this.totalInvestedAmount =
                totalInvestedAmount;
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
    public void setProfitOrLoss(
            double profitOrLoss) {

        // Profit or loss is derived from the difference between current value and invested amount.
        this.profitOrLoss =
                profitOrLoss;
    }


    /**
     * Returns the return percentage.
     * @return percentage return calculated from profit or loss against invested amount.
     */
    public double getReturnPercentage() {

        return returnPercentage;
    }


    /**
     * Updates the return percentage carried by this DTO.
     * @param returnPercentage percentage return calculated from profit or loss against invested amount.
     */
    public void setReturnPercentage(
            double returnPercentage) {

        this.returnPercentage =
                returnPercentage;
    }
}