package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.FinancialActivity;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
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
 * Represents the redemption of mutual-fund units and the resulting settlement values.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "redemption")
public class Redemption extends FinancialActivity {

    /**
     * Unique identifier of the redemption.
     */
    @Id
    @Column(name = "redemption_id", length = 50)
    private String redemptionId;

    /**
     * Investor associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Investor investor;

    /**
     * Mutual fund associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", nullable = false)
    private MutualFund mutualFund;

    /**
     * Transaction associated with this redemption.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    /**
     * Number of mutual-fund units redeemed.
     */
    @Column(name = "units_redeemed", nullable = false)
    private double unitsRedeemed;

    /**
     * NAV used when calculating the redemption.
     */
    @Column(name = "nav_at_redemption", nullable = false)
    private double navAtRedemption;

    /**
     * Gross redemption amount before charges.
     */
    @Column(name = "gross_amount", nullable = false)
    private double grossAmount;

    /**
     * Brokerage charges applied to the redemption.
     */
    @Column(name = "brokerage_charges", nullable = false)
    private double brokerageCharges;

    /**
     * Net amount received after redemption charges.
     */
    @Column(name = "amount_received", nullable = false)
    private double amountReceived;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Redemption() {
    }

    /**
     * Returns the redemption id.
     *
     * @return redemption id
     */
    public String getRedemptionId() {
        return redemptionId;
    }

    /**
     * Updates the redemption id.
     *
     * @param redemptionId new redemption id value
     */
    public void setRedemptionId(String redemptionId) {
        this.redemptionId = redemptionId;
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
     * Returns the transaction.
     *
     * @return transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Updates the transaction.
     *
     * @param transaction new transaction value
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Returns the units redeemed.
     *
     * @return units redeemed
     */
    public double getUnitsRedeemed() {
        return unitsRedeemed;
    }

    /**
     * Updates the units redeemed.
     *
     * @param unitsRedeemed new units redeemed value
     */
    public void setUnitsRedeemed(double unitsRedeemed) {
        this.unitsRedeemed = unitsRedeemed;
    }

    /**
     * Returns the nav at redemption.
     *
     * @return nav at redemption
     */
    public double getNavAtRedemption() {
        return navAtRedemption;
    }

    /**
     * Updates the nav at redemption.
     *
     * @param navAtRedemption new nav at redemption value
     */
    public void setNavAtRedemption(double navAtRedemption) {
        this.navAtRedemption = navAtRedemption;
    }

    /**
     * Returns the gross amount.
     *
     * @return gross amount
     */
    public double getGrossAmount() {
        return grossAmount;
    }

    /**
     * Updates the gross amount.
     *
     * @param grossAmount new gross amount value
     */
    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    /**
     * Returns the brokerage charges.
     *
     * @return brokerage charges
     */
    public double getBrokerageCharges() {
        return brokerageCharges;
    }

    /**
     * Updates the brokerage charges.
     *
     * @param brokerageCharges new brokerage charges value
     */
    public void setBrokerageCharges(double brokerageCharges) {
        this.brokerageCharges = brokerageCharges;
    }

    /**
     * Returns the amount received.
     *
     * @return amount received
     */
    public double getAmountReceived() {
        return amountReceived;
    }

    /**
     * Updates the amount received.
     *
     * @param amountReceived new amount received value
     */
    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    /**
     * Returns a readable representation of the Redemption object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Redemption{" +
                "redemptionId='" + redemptionId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", mutualFund=" + (mutualFund != null ? mutualFund.getFundId() : null) +
                ", transaction=" + (transaction != null ? transaction.getTransactionId() : null) +
                ", unitsRedeemed=" + unitsRedeemed +
                ", navAtRedemption=" + navAtRedemption +
                ", grossAmount=" + grossAmount +
                ", brokerageCharges=" + brokerageCharges +
                ", amountReceived=" + amountReceived +
                ", amount=" + getAmount() +
                ", activityDate=" + getActivityDate() +
                '}';
    }

    /**
     * Compares this Redemption with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Redemption that = (Redemption) o;
        return Objects.equals(redemptionId, that.redemptionId);
    }

    /**
     * Returns a hash code consistent with the equality definition of Redemption.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(redemptionId);
    }
}
