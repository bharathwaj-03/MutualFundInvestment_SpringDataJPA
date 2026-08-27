package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents payment information recorded for an investor transaction.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "payment")
public class Payment {

    /**
     * Unique identifier of the payment record.
     */
    @Id
    @Column(name = "payment_id", length = 30)
    private String paymentId;

    /**
     * Investor associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

    /**
     * Payment method selected for the transaction.
     */
    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;

    /**
     * UPI identifier used for the payment.
     */
    @Column(name = "upi_id")
    private String upiId;

    /**
     * Card number used for card payment.
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * Name of the card holder.
     */
    @Column(name = "card_holder_name", length = 100)
    private String cardHolderName;

    /**
     * Name of the bank used for payment.
     */
    @Column(name = "bank_name", length = 100)
    private String bankName;

    /**
     * Bank account number associated with the record.
     */
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * Current processing status of the payment.
     */
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    /**
     * Date and time when the payment was recorded.
     */
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Payment() {
    }

    /**
     * Returns the payment id.
     *
     * @return payment id
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * Updates the payment id.
     *
     * @param paymentId new payment id value
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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
     * Returns the payment method.
     *
     * @return payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Updates the payment method.
     *
     * @param paymentMethod new payment method value
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the upi id.
     *
     * @return upi id
     */
    public String getUpiId() {
        return upiId;
    }

    /**
     * Updates the upi id.
     *
     * @param upiId new upi id value
     */
    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    /**
     * Returns the card number.
     *
     * @return card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Updates the card number.
     *
     * @param cardNumber new card number value
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Returns the card holder name.
     *
     * @return card holder name
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * Updates the card holder name.
     *
     * @param cardHolderName new card holder name value
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * Returns the bank name.
     *
     * @return bank name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Updates the bank name.
     *
     * @param bankName new bank name value
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Returns the account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Updates the account number.
     *
     * @param accountNumber new account number value
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Returns the payment status.
     *
     * @return payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Updates the payment status.
     *
     * @param paymentStatus new payment status value
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Returns the payment date.
     *
     * @return payment date
     */
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    /**
     * Updates the payment date.
     *
     * @param paymentDate new payment date value
     */
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Returns a readable representation of the Payment object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
