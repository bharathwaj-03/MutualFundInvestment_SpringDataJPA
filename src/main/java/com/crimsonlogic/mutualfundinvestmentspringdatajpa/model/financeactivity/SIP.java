package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a Systematic Investment Plan created for recurring mutual-fund investments.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "sip")
public class SIP {

    /**
     * Unique identifier of the SIP.
     */
    @Id
    @Column(name = "sip_id", length = 20)
    private String sipId;

    /**
     * Investor associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Investor investor;

    /**
     * Mutual fund associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id")
    private MutualFund mutualFund;

    /**
     * Amount invested for each SIP installment.
     */
    @Column(name = "monthly_amount")
    private double monthlyAmount;

    /**
     * Number of mutual-fund units purchased.
     */
    @Column(name = "units_purchased")
    private double unitsPurchased;

    /**
     * Date on which the financial activity occurred.
     */
    @Column(name = "activity_date")
    private LocalDate activityDate;

    /**
     * Date on which the SIP begins.
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * Date on which the next SIP installment is due.
     */
    @Column(name = "next_installment_date")
    private LocalDate nextInstallmentDate;

    /**
     * Number of years for which the investment is planned.
     */
    @Column(name = "investment_years")
    private int investmentYears;

    /**
     * Calculated or expected asset gain for one year.
     */
    @Column(name = "asset_gain_per_year")
    private double assetGainPerYear;

    /**
     * Calculated or expected total gain over the investment period.
     */
    @Column(name = "asset_gain_total_invested_years")
    private double assetGainTotalInvestedYears;

    /**
     * Current status of the SIP.
     */
    @Column(name = "sip_status", length = 20)
    private String sipStatus;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public SIP() {
    }

    /**
     * Returns the sip id.
     *
     * @return sip id
     */
    public String getSipId() {
        return sipId;
    }

    /**
     * Updates the sip id.
     *
     * @param sipId new sip id value
     */
    public void setSipId(String sipId) {
        this.sipId = sipId;
    }

    /**
     * Returns the investor.
     *
     * @return investor
     */
    public Investor getInvestor() {
        return investor;
    }

    /**
     * Updates the investor.
     *
     * @param investor new investor value
     */
    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    /**
     * Returns the mutual fund.
     *
     * @return mutual fund
     */
    public MutualFund getMutualFund() {
        return mutualFund;
    }

    /**
     * Updates the mutual fund.
     *
     * @param mutualFund new mutual fund value
     */
    public void setMutualFund(MutualFund mutualFund) {
        this.mutualFund = mutualFund;
    }

    /**
     * Returns the monthly amount.
     *
     * @return monthly amount
     */
    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    /**
     * Updates the monthly amount.
     *
     * @param monthlyAmount new monthly amount value
     */
    public void setMonthlyAmount(double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    /**
     * Returns the units purchased.
     *
     * @return units purchased
     */
    public double getUnitsPurchased() {
        return unitsPurchased;
    }

    /**
     * Updates the units purchased.
     *
     * @param unitsPurchased new units purchased value
     */
    public void setUnitsPurchased(double unitsPurchased) {
        this.unitsPurchased = unitsPurchased;
    }

    /**
     * Returns the activity date.
     *
     * @return activity date
     */
    public LocalDate getActivityDate() {
        return activityDate;
    }

    /**
     * Updates the activity date.
     *
     * @param activityDate new activity date value
     */
    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    /**
     * Returns the start date.
     *
     * @return start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Updates the start date.
     *
     * @param startDate new start date value
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the next installment date.
     *
     * @return next installment date
     */
    public LocalDate getNextInstallmentDate() {
        return nextInstallmentDate;
    }

    /**
     * Updates the next installment date.
     *
     * @param nextInstallmentDate new next installment date value
     */
    public void setNextInstallmentDate(LocalDate nextInstallmentDate) {
        this.nextInstallmentDate = nextInstallmentDate;
    }

    /**
     * Returns the investment years.
     *
     * @return investment years
     */
    public int getInvestmentYears() {
        return investmentYears;
    }

    /**
     * Updates the investment years.
     *
     * @param investmentYears new investment years value
     */
    public void setInvestmentYears(int investmentYears) {
        this.investmentYears = investmentYears;
    }

    /**
     * Returns the asset gain per year.
     *
     * @return asset gain per year
     */
    public double getAssetGainPerYear() {
        return assetGainPerYear;
    }

    /**
     * Updates the asset gain per year.
     *
     * @param assetGainPerYear new asset gain per year value
     */
    public void setAssetGainPerYear(double assetGainPerYear) {
        this.assetGainPerYear = assetGainPerYear;
    }

    /**
     * Returns the asset gain total invested years.
     *
     * @return asset gain total invested years
     */
    public double getAssetGainTotalInvestedYears() {
        return assetGainTotalInvestedYears;
    }

    /**
     * Updates the asset gain total invested years.
     *
     * @param assetGainTotalInvestedYears new asset gain total invested years value
     */
    public void setAssetGainTotalInvestedYears(double assetGainTotalInvestedYears) {
        this.assetGainTotalInvestedYears = assetGainTotalInvestedYears;
    }

    /**
     * Returns the sip status.
     *
     * @return sip status
     */
    public String getSipStatus() {
        return sipStatus;
    }

    /**
     * Updates the sip status.
     *
     * @param sipStatus new sip status value
     */
    public void setSipStatus(String sipStatus) {
        this.sipStatus = sipStatus;
    }

    /**
     * Returns a readable representation of the SIP object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "SIP{" +
                "sipId='" + sipId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", mutualFund=" + (mutualFund != null ? mutualFund.getFundId() : null) +
                ", monthlyAmount=" + monthlyAmount +
                ", unitsPurchased=" + unitsPurchased +
                ", activityDate=" + activityDate +
                ", startDate=" + startDate +
                ", nextInstallmentDate=" + nextInstallmentDate +
                ", investmentYears=" + investmentYears +
                ", assetGainPerYear=" + assetGainPerYear +
                ", assetGainTotalInvestedYears=" + assetGainTotalInvestedYears +
                ", sipStatus='" + sipStatus + '\'' +
                '}';
    }

    /**
     * Compares this SIP with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SIP sip = (SIP) o;
        return Objects.equals(sipId, sip.sipId);
    }

    /**
     * Returns a hash code consistent with the equality definition of SIP.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(sipId);
    }
}
