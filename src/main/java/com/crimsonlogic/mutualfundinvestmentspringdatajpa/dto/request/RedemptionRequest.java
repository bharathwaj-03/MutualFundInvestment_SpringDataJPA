package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object used to receive redemption data
 * from an API request.
 */
public class RedemptionRequest {

    /**
     * Unique identifier of the investor.
     */
    private String investorId;


    /**
     * Unique identifier of the holding being redeemed.
     */
    @NotBlank(
            message = "Holding ID is required."
    )
    private String holdingId;


    /**
     * Number of mutual fund units requested for redemption.
     */
    @NotNull(
            message = "Units are required."
    )
    @DecimalMin(
            value = "1.0",
            inclusive = true,
            message = "Units to redeem must be at least 1."
    )
    private Double units;


    public RedemptionRequest() {
    }


    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(
            String investorId) {

        this.investorId = investorId;
    }


    public String getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(
            String holdingId) {

        this.holdingId = holdingId;
    }


    public Double getUnits() {
        return units;
    }

    public void setUnits(
            Double units) {

        this.units = units;
    }
}