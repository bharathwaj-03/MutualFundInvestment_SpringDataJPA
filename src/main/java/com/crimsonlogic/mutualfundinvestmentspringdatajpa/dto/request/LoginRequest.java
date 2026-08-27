package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive login data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */

import javax.validation.constraints.NotBlank;
public class LoginRequest {
    /**
     * Unique identifier of the user attempting authentication.
     */
    @NotBlank(message = "User ID is required.")
    private String userId;

    @NotBlank(message = "Password is required.")
    private String password;

    /**
     * Creates a LoginRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public LoginRequest() {}

    /**
     * Returns the user id.
     * @return unique identifier of the user attempting authentication.
     */
    public String getUserId() { return userId; }
    /**
     * Updates the user id carried by this DTO.
     * @param userId unique identifier of the user attempting authentication.
     */
    public void setUserId(String userId) { this.userId = userId; }
    /**
     * Returns the password.
     * @return password supplied for authentication.
     */
    public String getPassword() { return password; }
    /**
     * Updates the password carried by this DTO.
     * @param password password supplied for authentication.
     */
    public void setPassword(String password) { this.password = password; }
}
