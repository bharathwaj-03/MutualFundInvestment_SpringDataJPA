package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Abstract JPA entity that defines common information and behavior for investment transactions.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING, length = 30)
public abstract class Transaction {

    /**
     * Unique identifier of the transaction.
     */
    @Id
    @Column(name = "transaction_id", length = 20)
    private String transactionId;

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
     * Payment record associated with the transaction.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    /**
     * Monetary amount associated with the activity or transaction.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Date and time when the transaction was recorded.
     */
    @Column(name = "transaction_datetime")
    private LocalDateTime transactionDateTime;

    /**
     * Current status of the transaction.
     */
    @Column(name = "transaction_status", length = 20)
    private String transactionStatus;

    /**
     * Type used to classify the transaction.
     */
    // transaction_type is the discriminator column, therefore this property is read-only.
    @Column(name = "transaction_type", insertable = false, updatable = false, length = 30)
    private String transactionType;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Transaction() {
    }

    /**
     * Executes the behavior defined for this concrete transaction type.
     */
    public abstract void executeTransaction();

    /**
     * Returns the transaction id.
     *
     * @return transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Updates the transaction id.
     *
     * @param transactionId new transaction id value
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
     * Returns the payment.
     *
     * @return payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Updates the payment.
     *
     * @param payment new payment value
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Returns the amount.
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Updates the amount.
     *
     * @param amount new amount value
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the transaction date time.
     *
     * @return transaction date time
     */
    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * Updates the transaction date time.
     *
     * @param transactionDateTime new transaction date time value
     */
    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    /**
     * Returns the transaction status.
     *
     * @return transaction status
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Updates the transaction status.
     *
     * @param transactionStatus new transaction status value
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Returns the transaction type.
     *
     * @return transaction type
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Updates the transaction type.
     *
     * @param transactionType new transaction type value
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Returns a readable representation of the Transaction object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", mutualFund=" + (mutualFund != null ? mutualFund.getFundId() : null) +
                ", payment=" + (payment != null ? payment.getPaymentId() : null) +
                ", amount=" + amount +
                ", transactionDateTime=" + transactionDateTime +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
