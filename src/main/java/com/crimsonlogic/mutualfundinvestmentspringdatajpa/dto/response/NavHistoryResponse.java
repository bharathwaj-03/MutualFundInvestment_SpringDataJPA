package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDate;

/**
 * Data transfer object used to return nav history information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class NavHistoryResponse {

    /**
     * Unique identifier of the NAV history record.
     */
    private String historyId;
    /**
     * Unique identifier of the mutual fund.
     */
    private String fundId;
    /**
     * Display name of the mutual fund.
     */
    private String fundName;
    /**
     * NAV value before the update.
     */
    private double oldNav;
    /**
     * Updated Net Asset Value supplied by the administrator.
     */
    private double newNav;
    /**
     * Date on which the NAV change was recorded.
     */
    private LocalDate changeDate;
    /**
     * Identifier of the administrator who changed the NAV.
     */
    private String changedBy;

    /**
     * Creates a NavHistoryResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public NavHistoryResponse() {
    }

    /**
     * Returns the history id.
     * @return unique identifier of the NAV history record.
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * Updates the history id carried by this DTO.
     * @param historyId unique identifier of the NAV history record.
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /**
     * Returns the fund id.
     * @return unique identifier of the mutual fund.
     */
    public String getFundId() {
        return fundId;
    }

    /**
     * Updates the fund id carried by this DTO.
     * @param fundId unique identifier of the mutual fund.
     */
    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    /**
     * Returns the fund name.
     * @return display name of the mutual fund.
     */
    public String getFundName() {
        return fundName;
    }

    /**
     * Updates the fund name carried by this DTO.
     * @param fundName display name of the mutual fund.
     */
    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    /**
     * Returns the old nav.
     * @return nAV value before the update.
     */
    public double getOldNav() {
        return oldNav;
    }

    /**
     * Updates the old nav carried by this DTO.
     * @param oldNav nAV value before the update.
     */
    public void setOldNav(double oldNav) {
        this.oldNav = oldNav;
    }

    /**
     * Returns the new nav.
     * @return updated Net Asset Value supplied by the administrator.
     */
    public double getNewNav() {
        return newNav;
    }

    /**
     * Updates the new nav carried by this DTO.
     * @param newNav updated Net Asset Value supplied by the administrator.
     */
    public void setNewNav(double newNav) {
        this.newNav = newNav;
    }

    /**
     * Returns the change date.
     * @return date on which the NAV change was recorded.
     */
    public LocalDate getChangeDate() {
        return changeDate;
    }

    /**
     * Updates the change date carried by this DTO.
     * @param changeDate date on which the NAV change was recorded.
     */
    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * Returns the changed by.
     * @return identifier of the administrator who changed the NAV.
     */
    public String getChangedBy() {
        return changedBy;
    }

    /**
     * Updates the changed by carried by this DTO.
     * @param changedBy identifier of the administrator who changed the NAV.
     */
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}