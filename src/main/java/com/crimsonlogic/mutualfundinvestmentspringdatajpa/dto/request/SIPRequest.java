package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

import java.time.LocalDate;

/**
 * Data transfer object used to receive s i p data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class SIPRequest {
    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;
    /**
     * Unique identifier of the mutual fund.
     */
    private String fundId;
    /**
     * Monthly amount selected for the SIP.
     */
    private double monthlyAmount;
    /**
     * Number of years selected for the investment period.
     */
    private int investmentYears;
    /**
     * Date on which the SIP is scheduled to begin.
     */
    private LocalDate startDate;
    /**
     * Payment details supplied for processing the investment.
     */
    private PaymentRequest payment;

    /**
     * Creates a SIPRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public SIPRequest() {}

    /**
     * Returns the investor id.
     * @return unique identifier of the investor associated with the request or response.
     */
    public String getInvestorId() { return investorId; }
    /**
     * Updates the investor id carried by this DTO.
     * @param investorId unique identifier of the investor associated with the request or response.
     */
    public void setInvestorId(String investorId) { this.investorId = investorId; }
    /**
     * Returns the fund id.
     * @return unique identifier of the mutual fund.
     */
    public String getFundId() { return fundId; }
    /**
     * Updates the fund id carried by this DTO.
     * @param fundId unique identifier of the mutual fund.
     */
    public void setFundId(String fundId) { this.fundId = fundId; }
    /**
     * Returns the monthly amount.
     * @return monthly amount selected for the SIP.
     */
    public double getMonthlyAmount() { return monthlyAmount; }
    /**
     * Updates the monthly amount carried by this DTO.
     * @param monthlyAmount monthly amount selected for the SIP.
     */
    public void setMonthlyAmount(double monthlyAmount) { this.monthlyAmount = monthlyAmount; }
    /**
     * Returns the investment years.
     * @return number of years selected for the investment period.
     */
    public int getInvestmentYears() { return investmentYears; }
    /**
     * Updates the investment years carried by this DTO.
     * @param investmentYears number of years selected for the investment period.
     */
    public void setInvestmentYears(int investmentYears) { this.investmentYears = investmentYears; }
    /**
     * Returns the start date.
     * @return date on which the SIP is scheduled to begin.
     */
    public LocalDate getStartDate() { return startDate; }
    /**
     * Updates the start date carried by this DTO.
     * @param startDate date on which the SIP is scheduled to begin.
     */
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    /**
     * Returns the payment.
     * @return payment details supplied for processing the investment.
     */
    public PaymentRequest getPayment() { return payment; }
    /**
     * Updates the payment carried by this DTO.
     * @param payment payment details supplied for processing the investment.
     */
    public void setPayment(PaymentRequest payment) { this.payment = payment; }
}
