package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * Request DTO used when an administrator updates
 * the NAV of a mutual fund.
 */
public class NavUpdateRequest {

    /**
     * New NAV value to assign to the fund.
     */
    @NotNull(
            message =
                    "New NAV is required."
    )
    @Positive(
            message =
                    "NAV must be greater than 0."
    )
    private Double newNav;


    /**
     * Identifier of the administrator performing the NAV update.
     */
    @NotBlank(
            message =
                    "Admin ID is required."
    )
    @Pattern(
            regexp =
                    "^ADM[0-9]{3}$",
            message =
                    "Invalid Admin ID. Example: ADM001."
    )
    private String adminId;


    public NavUpdateRequest() {
    }


    public Double getNewNav() {
        return newNav;
    }


    public void setNewNav(
            Double newNav) {

        this.newNav =
                newNav;
    }


    public String getAdminId() {
        return adminId;
    }


    public void setAdminId(
            String adminId) {

        this.adminId =
                adminId;
    }
}