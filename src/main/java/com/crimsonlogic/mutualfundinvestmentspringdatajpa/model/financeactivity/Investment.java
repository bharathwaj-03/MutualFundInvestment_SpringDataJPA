package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.FinancialActivity;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a lump-sum mutual-fund investment made by an investor.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "investment")
public class Investment extends FinancialActivity {

    /**
     * Unique identifier of the investment.
     */
    @Id
    @Column(name = "investment_id", length = 20)
    private String investmentId;

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
     * Number of mutual-fund units purchased.
     */
    @Column(name = "units_purchased")
    private double unitsPurchased;

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
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Investment() {
    }

    /**
     * Returns the investment id.
     *
     * @return investment id
     */
    public String getInvestmentId() {
        return investmentId;
    }

    /**
     * Updates the investment id.
     *
     * @param investmentId new investment id value
     */
    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
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
     * Returns a readable representation of the Investment object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Investment{" +
                "investmentId='" + investmentId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", mutualFund=" + (mutualFund != null ? mutualFund.getFundId() : null) +
                ", amount=" + getAmount() +
                ", activityDate=" + getActivityDate() +
                ", unitsPurchased=" + unitsPurchased +
                ", investmentYears=" + investmentYears +
                ", assetGainPerYear=" + assetGainPerYear +
                ", assetGainTotalInvestedYears=" + assetGainTotalInvestedYears +
                '}';
    }

    /**
     * Compares this Investment with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investment that = (Investment) o;
        return Objects.equals(investmentId, that.investmentId);
    }

    /**
     * Returns a hash code consistent with the equality definition of Investment.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(investmentId);
    }
}
