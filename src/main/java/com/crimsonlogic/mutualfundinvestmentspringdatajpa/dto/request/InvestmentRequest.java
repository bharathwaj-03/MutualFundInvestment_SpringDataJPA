package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InvestmentRequest {

    @NotBlank(
            message = "Investor ID is required."
    )
    private String investorId;


    @NotBlank(
            message = "Fund ID is required."
    )
    private String fundId;


    @Positive(
            message = "Investment amount must be greater than 0."
    )
    private double amount;


    @Min(
            value = 1,
            message = "Investment period must be at least 1 year."
    )
    private int investmentYears;


    @NotNull(
            message = "Payment details are required."
    )
    @Valid
    private PaymentRequest payment;


    public InvestmentRequest() {
    }


    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }


    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public int getInvestmentYears() {
        return investmentYears;
    }

    public void setInvestmentYears(int investmentYears) {
        this.investmentYears = investmentYears;
    }


    public PaymentRequest getPayment() {
        return payment;
    }

    public void setPayment(PaymentRequest payment) {
        this.payment = payment;
    }
}