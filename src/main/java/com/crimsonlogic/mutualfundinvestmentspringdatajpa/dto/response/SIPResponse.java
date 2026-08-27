package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDate;

/**
 * Data transfer object used to return s i p information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class SIPResponse {

    /**
     * Unique identifier of the SIP.
     */
    private String sipId;

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
     * Monthly amount selected for the SIP.
     */
    private double monthlyAmount;

    /**
     * Number of mutual fund units purchased.
     */
    private double unitsPurchased;

    /**
     * Date on which the investment activity occurred.
     */
    private LocalDate activityDate;

    /**
     * Date on which the SIP is scheduled to begin.
     */
    private LocalDate startDate;

    /**
     * Date on which the next SIP installment is due.
     */
    private LocalDate nextInstallmentDate;

    /**
     * Number of years selected for the investment period.
     */
    private int investmentYears;

    /**
     * Expected gain amount for one year.
     */
    private double assetGainPerYear;

    /**
     * Expected total gain over the complete investment period.
     */
    private double assetGainTotalInvestedYears;

    /**
     * Current status of the SIP.
     */
    private String sipStatus;


    /**
     * Creates a SIPResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public SIPResponse() {
    }


    /**
     * Returns the sip id.
     * @return unique identifier of the SIP.
     */
    public String getSipId() {
        return sipId;
    }

    /**
     * Updates the sip id carried by this DTO.
     * @param sipId unique identifier of the SIP.
     */
    public void setSipId(String sipId) {
        this.sipId = sipId;
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
     * Returns the monthly amount.
     * @return monthly amount selected for the SIP.
     */
    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    /**
     * Updates the monthly amount carried by this DTO.
     * @param monthlyAmount monthly amount selected for the SIP.
     */
    public void setMonthlyAmount(double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }


    /**
     * Returns the units purchased.
     * @return number of mutual fund units purchased.
     */
    public double getUnitsPurchased() {
        return unitsPurchased;
    }

    /**
     * Updates the units purchased carried by this DTO.
     * @param unitsPurchased number of mutual fund units purchased.
     */
    public void setUnitsPurchased(double unitsPurchased) {
        this.unitsPurchased = unitsPurchased;
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
    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }


    /**
     * Returns the start date.
     * @return date on which the SIP is scheduled to begin.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Updates the start date carried by this DTO.
     * @param startDate date on which the SIP is scheduled to begin.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    /**
     * Returns the next installment date.
     * @return date on which the next SIP installment is due.
     */
    public LocalDate getNextInstallmentDate() {
        return nextInstallmentDate;
    }

    /**
     * Updates the next installment date carried by this DTO.
     * @param nextInstallmentDate date on which the next SIP installment is due.
     */
    public void setNextInstallmentDate(
            LocalDate nextInstallmentDate) {

        this.nextInstallmentDate =
                nextInstallmentDate;
    }


    /**
     * Returns the investment years.
     * @return number of years selected for the investment period.
     */
    public int getInvestmentYears() {
        return investmentYears;
    }

    /**
     * Updates the investment years carried by this DTO.
     * @param investmentYears number of years selected for the investment period.
     */
    public void setInvestmentYears(
            int investmentYears) {

        this.investmentYears =
                investmentYears;
    }


    /**
     * Returns the asset gain per year.
     * @return expected gain amount for one year.
     */
    public double getAssetGainPerYear() {
        return assetGainPerYear;
    }

    /**
     * Updates the asset gain per year carried by this DTO.
     * @param assetGainPerYear expected gain amount for one year.
     */
    public void setAssetGainPerYear(
            double assetGainPerYear) {

        this.assetGainPerYear =
                assetGainPerYear;
    }


    /**
     * Returns the asset gain total invested years.
     * @return expected total gain over the complete investment period.
     */
    public double getAssetGainTotalInvestedYears() {
        return assetGainTotalInvestedYears;
    }

    /**
     * Updates the asset gain total invested years carried by this DTO.
     * @param assetGainTotalInvestedYears expected total gain over the complete investment period.
     */
    public void setAssetGainTotalInvestedYears(
            double assetGainTotalInvestedYears) {

        this.assetGainTotalInvestedYears =
                assetGainTotalInvestedYears;
    }


    /**
     * Returns the sip status.
     * @return current status of the SIP.
     */
    public String getSipStatus() {
        return sipStatus;
    }

    /**
     * Updates the sip status carried by this DTO.
     * @param sipStatus current status of the SIP.
     */
    public void setSipStatus(String sipStatus) {
        this.sipStatus = sipStatus;
    }
}