package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

/**
 * Data transfer object used to return inactive investor information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class InactiveInvestorResponse {

    /**
     * Unique identifier of the user attempting authentication.
     */
    private String userId;
    /**
     * Name carried by the request or response object.
     */
    private String name;
    /**
     * Value representing the email.
     */
    private String email;
    /**
     * Value representing the phone number.
     */
    private String phoneNumber;
    /**
     * Risk profile selected for the investor.
     */
    private String riskProfile;
    /**
     * Indicates whether the investor account is active.
     */
    private boolean active;

    /**
     * Creates a InactiveInvestorResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public InactiveInvestorResponse() {
    }

    /**
     * Creates a InactiveInvestorResponse object with the supplied data.
     * @param userId Unique identifier of the user attempting authentication.
     * @param name Name carried by the request or response object.
     * @param email email
     * @param phoneNumber phone number
     * @param riskProfile Risk profile selected for the investor.
     * @param active Indicates whether the investor account is active.
     */
    public InactiveInvestorResponse(
            String userId,
            String name,
            String email,
            String phoneNumber,
            String riskProfile,
            boolean active) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.riskProfile = riskProfile;
        this.active = active;
    }

    /**
     * Returns the user id.
     * @return unique identifier of the user attempting authentication.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Updates the user id carried by this DTO.
     * @param userId unique identifier of the user attempting authentication.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the name.
     * @return name carried by the request or response object.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name carried by this DTO.
     * @param name name carried by the request or response object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email carried by this DTO.
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the phone number carried by this DTO.
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the risk profile.
     * @return risk profile selected for the investor.
     */
    public String getRiskProfile() {
        return riskProfile;
    }

    /**
     * Updates the risk profile carried by this DTO.
     * @param riskProfile risk profile selected for the investor.
     */
    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    /**
     * Returns the active.
     * @return indicates whether the investor account is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Updates the active carried by this DTO.
     * @param active indicates whether the investor account is active.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
