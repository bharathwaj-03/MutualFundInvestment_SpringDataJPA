package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Abstract JPA entity that defines common information shared by all mutual-fund categories.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "mutualfund")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fund_category", discriminatorType = DiscriminatorType.STRING, length = 30)
public abstract class MutualFund implements Comparable<MutualFund> {

    /**
     * Unique identifier of the mutual fund.
     */
    @Id
    @Column(name = "fund_id", length = 20)
    private String fundId;

    /**
     * Display name of the mutual fund.
     */
    @Column(name = "fund_name", nullable = false, length = 100)
    private String fundName;

    /**
     * Category used to distinguish the concrete mutual-fund type.
     */
    // fund_category is used as the JPA discriminator, so this property is read-only.
    @Column(name = "fund_category", insertable = false, updatable = false, length = 30)
    private String fundCategory;

    /**
     * Fund house or asset-management company that manages the mutual fund.
     */
    @Column(name = "fund_house", nullable = false, length = 100)
    private String fundHouse;

    /**
     * Risk classification assigned to the mutual fund.
     */
    @Column(name = "risk_level", nullable = false, length = 20)
    private String riskLevel;

    /**
     * Current Net Asset Value of the mutual fund.
     */
    @Column(name = "nav", nullable = false)
    private double nav;

    /**
     * Minimum amount required to invest in the mutual fund.
     */
    @Column(name = "minimum_investment", nullable = false)
    private double minimumInvestment;

    /**
     * Expected annual gain percentage used for SIP calculations.
     */
    @Column(name = "sip_gain_per_year", nullable = false)
    private double sipGainPerYear;

    /**
     * Expected annual gain percentage used for lump-sum calculations.
     */
    @Column(name = "lumpsum_gain_per_year", nullable = false)
    private double lumpSumGainPerYear;

    /**
     * Business code used to identify the mutual fund.
     */
    @Column(name = "fund_code", length = 30)
    private String fundCode;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public MutualFund() {
    }

    /**
     * Returns the fund id.
     *
     * @return fund id
     */
    public String getFundId() {
        return fundId;
    }

    /**
     * Updates the fund id.
     *
     * @param fundId new fund id value
     */
    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    /**
     * Returns the fund name.
     *
     * @return fund name
     */
    public String getFundName() {
        return fundName;
    }

    /**
     * Updates the fund name.
     *
     * @param fundName new fund name value
     */
    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    /**
     * Returns the fund category.
     *
     * @return fund category
     */
    public String getFundCategory() {
        return fundCategory;
    }

    /**
     * Updates the fund category.
     *
     * @param fundCategory new fund category value
     */
    public void setFundCategory(String fundCategory) {
        this.fundCategory = fundCategory;
    }

    /**
     * Returns the fund house.
     *
     * @return fund house
     */
    public String getFundHouse() {
        return fundHouse;
    }

    /**
     * Updates the fund house.
     *
     * @param fundHouse new fund house value
     */
    public void setFundHouse(String fundHouse) {
        this.fundHouse = fundHouse;
    }

    /**
     * Returns the risk level.
     *
     * @return risk level
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * Updates the risk level.
     *
     * @param riskLevel new risk level value
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * Returns the nav.
     *
     * @return nav
     */
    public double getNav() {
        return nav;
    }

    /**
     * Updates the nav.
     *
     * @param nav new nav value
     */
    public void setNav(double nav) {
        this.nav = nav;
    }

    /**
     * Returns the minimum investment.
     *
     * @return minimum investment
     */
    public double getMinimumInvestment() {
        return minimumInvestment;
    }

    /**
     * Updates the minimum investment.
     *
     * @param minimumInvestment new minimum investment value
     */
    public void setMinimumInvestment(double minimumInvestment) {
        this.minimumInvestment = minimumInvestment;
    }

    /**
     * Returns the sip gain per year.
     *
     * @return sip gain per year
     */
    public double getSipGainPerYear() {
        return sipGainPerYear;
    }

    /**
     * Updates the sip gain per year.
     *
     * @param sipGainPerYear new sip gain per year value
     */
    public void setSipGainPerYear(double sipGainPerYear) {
        this.sipGainPerYear = sipGainPerYear;
    }

    /**
     * Returns the lump sum gain per year.
     *
     * @return lump sum gain per year
     */
    public double getLumpSumGainPerYear() {
        return lumpSumGainPerYear;
    }

    /**
     * Updates the lump sum gain per year.
     *
     * @param lumpSumGainPerYear new lump sum gain per year value
     */
    public void setLumpSumGainPerYear(double lumpSumGainPerYear) {
        this.lumpSumGainPerYear = lumpSumGainPerYear;
    }

    /**
     * Returns the fund code.
     *
     * @return fund code
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * Updates the fund code.
     *
     * @param fundCode new fund code value
     */
    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    /**
     * Compares this MutualFund with another instance to provide a consistent ordering.
     *
     * @param other other supplied to the operation
     *
     * @return result produced by the compare to operation
     */
    @Override
    public int compareTo(MutualFund other) {
        return this.fundName.compareToIgnoreCase(other.fundName);
    }

    /**
     * Returns a readable representation of the MutualFund object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "MutualFund{" +
                "fundId='" + fundId + '\'' +
                ", fundName='" + fundName + '\'' +
                ", fundCategory='" + fundCategory + '\'' +
                ", fundHouse='" + fundHouse + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", nav=" + nav +
                ", minimumInvestment=" + minimumInvestment +
                ", sipGainPerYear=" + sipGainPerYear +
                ", lumpSumGainPerYear=" + lumpSumGainPerYear +
                ", fundCode='" + fundCode + '\'' +
                '}';
    }
}
