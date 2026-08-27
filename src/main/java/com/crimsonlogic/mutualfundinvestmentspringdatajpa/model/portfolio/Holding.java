package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "holding", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"portfolio_id", "fund_id"})
})
/**
 * Represents an investor portfolio position in a particular mutual fund.
 *
 * JPA maps this entity to its corresponding database representation.
 */
public class Holding {

    /**
     * Unique identifier of the holding.
     */
    @Id
    @Column(name = "holding_id", length = 20)
    private String holdingId;

    /**
     * Portfolio that owns this holding.
     *
     * Many records of this entity can reference the same associated record.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    /**
     * Mutual fund associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id")
    private MutualFund mutualFund;

    /**
     * Current number of mutual-fund units held.
     */
    @Column(name = "units_owned")
    private double unitsOwned;

    /**
     * Total amount invested in this holding.
     */
    @Column(name = "invested_amount")
    private double investedAmount;

    /**
     * Average NAV at which the holding units were acquired.
     */
    @Column(name = "average_nav")
    private double averageNav;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Holding() {
    }

    /**
     * Returns the holding id.
     *
     * @return holding id
     */
    public String getHoldingId() {
        return holdingId;
    }

    /**
     * Updates the holding id.
     *
     * @param holdingId new holding id value
     */
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    /**
     * Returns the portfolio.
     *
     * @return portfolio
     */
    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Updates the portfolio.
     *
     * @param portfolio new portfolio value
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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
     * Returns the units owned.
     *
     * @return units owned
     */
    public double getUnitsOwned() {
        return unitsOwned;
    }

    /**
     * Updates the units owned.
     *
     * @param unitsOwned new units owned value
     */
    public void setUnitsOwned(double unitsOwned) {
        this.unitsOwned = unitsOwned;
    }

    /**
     * Returns the invested amount.
     *
     * @return invested amount
     */
    public double getInvestedAmount() {
        return investedAmount;
    }

    /**
     * Updates the invested amount.
     *
     * @param investedAmount new invested amount value
     */
    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * Returns the average nav.
     *
     * @return average nav
     */
    public double getAverageNav() {
        return averageNav;
    }

    /**
     * Updates the average nav.
     *
     * @param averageNav new average nav value
     */
    public void setAverageNav(double averageNav) {
        this.averageNav = averageNav;
    }

    /**
     * Returns a readable representation of the Holding object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Holding{" +
                "holdingId='" + holdingId + '\'' +
                ", mutualFund=" + (mutualFund != null ? mutualFund.getFundId() : null) +
                ", unitsOwned=" + unitsOwned +
                ", investedAmount=" + investedAmount +
                ", averageNav=" + averageNav +
                '}';
    }

    /**
     * Compares this Holding with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holding holding = (Holding) o;
        return Objects.equals(holdingId, holding.holdingId);
    }

    /**
     * Returns a hash code consistent with the equality definition of Holding.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(holdingId);
    }
}
