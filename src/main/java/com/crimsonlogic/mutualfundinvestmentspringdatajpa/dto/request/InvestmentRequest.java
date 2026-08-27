package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive investment data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class InvestmentRequest {
    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;
    /**
     * Unique identifier of the mutual fund.
     */
    private String fundId;
    /**
     * Investment amount supplied for the transaction.
     */
    private double amount;
    /**
     * Number of years selected for the investment period.
     */
    private int investmentYears;
    /**
     * Payment details supplied for processing the investment.
     */
    private PaymentRequest payment;

    /**
     * Creates a InvestmentRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public InvestmentRequest() {}

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
     * Returns the amount.
     * @return investment amount supplied for the transaction.
     */
    public double getAmount() { return amount; }
    /**
     * Updates the amount carried by this DTO.
     * @param amount investment amount supplied for the transaction.
     */
    public void setAmount(double amount) { this.amount = amount; }
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
