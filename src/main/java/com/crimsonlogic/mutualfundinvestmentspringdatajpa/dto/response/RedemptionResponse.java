package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDate;

/**
 * Data transfer object used to return redemption information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class RedemptionResponse {

    /**
     * Unique identifier of the redemption transaction.
     */
    private String redemptionId;

    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;

    /**
     * Name of the investor associated with the response.
     */
    private String investorName;

    /**
     * Unique identifier of the mutual fund.
     */
    private String fundId;

    /**
     * Display name of the mutual fund.
     */
    private String fundName;

    /**
     * Unique identifier of the related transaction.
     */
    private String transactionId;

    /**
     * Number of units redeemed in the transaction.
     */
    private double unitsRedeemed;

    /**
     * Value representing the nav at redemption.
     */
    private double navAtRedemption;

    /**
     * Value representing the gross amount.
     */
    private double grossAmount;

    /**
     * Value representing the brokerage charges.
     */
    private double brokerageCharges;

    /**
     * Value representing the amount received.
     */
    private double amountReceived;

    /**
     * Date on which the investment activity occurred.
     */
    private LocalDate activityDate;


    /**
     * Creates a RedemptionResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public RedemptionResponse() {
    }


    /**
     * Returns the redemption id.
     * @return unique identifier of the redemption transaction.
     */
    public String getRedemptionId() {
        return redemptionId;
    }

    /**
     * Updates the redemption id carried by this DTO.
     * @param redemptionId unique identifier of the redemption transaction.
     */
    public void setRedemptionId(
            String redemptionId) {

        this.redemptionId =
                redemptionId;
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

        this.investorId =
                investorId;
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

        this.investorName =
                investorName;
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
    public void setFundId(
            String fundId) {

        this.fundId =
                fundId;
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
    public void setFundName(
            String fundName) {

        this.fundName =
                fundName;
    }


    /**
     * Returns the transaction id.
     * @return unique identifier of the related transaction.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Updates the transaction id carried by this DTO.
     * @param transactionId unique identifier of the related transaction.
     */
    public void setTransactionId(
            String transactionId) {

        this.transactionId =
                transactionId;
    }


    /**
     * Returns the units redeemed.
     * @return number of units redeemed in the transaction.
     */
    public double getUnitsRedeemed() {
        return unitsRedeemed;
    }

    /**
     * Updates the units redeemed carried by this DTO.
     * @param unitsRedeemed number of units redeemed in the transaction.
     */
    public void setUnitsRedeemed(
            double unitsRedeemed) {

        this.unitsRedeemed =
                unitsRedeemed;
    }


    /**
     * Returns the nav at redemption.
     * @return nav at redemption
     */
    public double getNavAtRedemption() {
        return navAtRedemption;
    }

    /**
     * Updates the nav at redemption carried by this DTO.
     * @param navAtRedemption nav at redemption
     */
    public void setNavAtRedemption(
            double navAtRedemption) {

        this.navAtRedemption =
                navAtRedemption;
    }


    /**
     * Returns the gross amount.
     * @return gross amount
     */
    public double getGrossAmount() {
        return grossAmount;
    }

    /**
     * Updates the gross amount carried by this DTO.
     * @param grossAmount gross amount
     */
    public void setGrossAmount(
            double grossAmount) {

        this.grossAmount =
                grossAmount;
    }


    /**
     * Returns the brokerage charges.
     * @return brokerage charges
     */
    public double getBrokerageCharges() {
        return brokerageCharges;
    }

    /**
     * Updates the brokerage charges carried by this DTO.
     * @param brokerageCharges brokerage charges
     */
    public void setBrokerageCharges(
            double brokerageCharges) {

        this.brokerageCharges =
                brokerageCharges;
    }


    /**
     * Returns the amount received.
     * @return amount received
     */
    public double getAmountReceived() {
        return amountReceived;
    }

    /**
     * Updates the amount received carried by this DTO.
     * @param amountReceived amount received
     */
    public void setAmountReceived(
            double amountReceived) {

        this.amountReceived =
                amountReceived;
    }


    /**
     * Returns the activity date.
     * @return date on which the investment activity occurred.
     */
    public LocalDate getActivityDate() {
        return activityDate;
    }

    /**
     * Updates the activity date carried by this DTO.
     * @param activityDate date on which the investment activity occurred.
     */
    public void setActivityDate(
            LocalDate activityDate) {

        this.activityDate =
                activityDate;
    }
}