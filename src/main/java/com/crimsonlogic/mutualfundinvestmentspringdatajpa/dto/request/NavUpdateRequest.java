package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive nav update data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class NavUpdateRequest {
    /**
     * Updated Net Asset Value supplied by the administrator.
     */
    private double newNav;
    /**
     * Identifier of the administrator performing the operation.
     */
    private String adminId;

    /**
     * Creates a NavUpdateRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public NavUpdateRequest() {}

    /**
     * Returns the new nav.
     * @return updated Net Asset Value supplied by the administrator.
     */
    public double getNewNav() { return newNav; }
    /**
     * Updates the new nav carried by this DTO.
     * @param newNav updated Net Asset Value supplied by the administrator.
     */
    public void setNewNav(double newNav) { this.newNav = newNav; }
    /**
     * Returns the admin id.
     * @return identifier of the administrator performing the operation.
     */
    public String getAdminId() { return adminId; }
    /**
     * Updates the admin id carried by this DTO.
     * @param adminId identifier of the administrator performing the operation.
     */
    public void setAdminId(String adminId) { this.adminId = adminId; }
}
