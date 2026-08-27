package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive redemption data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class RedemptionRequest {
    /**
     * Unique identifier of the investor associated with the request or response.
     */
    private String investorId;
    /**
     * Unique identifier of the holding being redeemed.
     */
    private String holdingId;
    /**
     * Number of mutual fund units requested for redemption.
     */
    private double units;

    /**
     * Creates a RedemptionRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public RedemptionRequest() {}

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
     * Returns the holding id.
     * @return unique identifier of the holding being redeemed.
     */
    public String getHoldingId() { return holdingId; }
    /**
     * Updates the holding id carried by this DTO.
     * @param holdingId unique identifier of the holding being redeemed.
     */
    public void setHoldingId(String holdingId) { this.holdingId = holdingId; }
    /**
     * Returns the units.
     * @return number of mutual fund units requested for redemption.
     */
    public double getUnits() { return units; }
    /**
     * Updates the units carried by this DTO.
     * @param units number of mutual fund units requested for redemption.
     */
    public void setUnits(double units) { this.units = units; }
}
