package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;


/**
 * Data transfer object used to receive SIP creation data.
 *
 * Bean Validation verifies request-format rules before
 * the SIP creation workflow reaches the service layer.
 */
public class SIPRequest {


    /**
     * Unique identifier of the mutual fund selected for the SIP.
     */
    @NotBlank(
            message =
                    "Fund ID is required."
    )
    private String fundId;


    /**
     * Monthly installment selected for the SIP.
     *
     * The actual minimum amount is determined from the
     * selected mutual fund inside the service layer.
     */
    @NotNull(
            message =
                    "Monthly SIP amount is required."
    )
    private Double monthlyAmount;


    /**
     * Number of years for which the SIP will continue.
     */
    @Min(
            value = 1,
            message =
                    "Investment period must be at least 1 year."
    )
    private int investmentYears;


    /**
     * SIP start date.
     *
     * @Future ensures that today and past dates are rejected,
     * so the earliest permitted date is tomorrow.
     */
    @NotNull(
            message =
                    "SIP start date is required."
    )
    @Future(
            message =
                    "SIP start date must be tomorrow or later."
    )
    private LocalDate startDate;


    /**
     * Payment information used for the first SIP installment.
     *
     * @Valid enables nested validation of PaymentRequest,
     * including UPI, CARD, and BANK-specific rules.
     */
    @NotNull(
            message =
                    "Payment details are required."
    )
    @Valid
    private PaymentRequest payment;


    /**
     * Creates an empty SIP request for JSON binding.
     */
    public SIPRequest() {
    }


    public String getFundId() {

        return fundId;
    }


    public void setFundId(
            String fundId) {

        this.fundId =
                fundId;
    }


    public Double getMonthlyAmount() {

        return monthlyAmount;
    }


    public void setMonthlyAmount(
            Double monthlyAmount) {

        this.monthlyAmount =
                monthlyAmount;
    }


    public int getInvestmentYears() {

        return investmentYears;
    }


    public void setInvestmentYears(
            int investmentYears) {

        this.investmentYears =
                investmentYears;
    }


    public LocalDate getStartDate() {

        return startDate;
    }


    public void setStartDate(
            LocalDate startDate) {

        this.startDate =
                startDate;
    }


    public PaymentRequest getPayment() {

        return payment;
    }


    public void setPayment(
            PaymentRequest payment) {

        this.payment =
                payment;
    }
}