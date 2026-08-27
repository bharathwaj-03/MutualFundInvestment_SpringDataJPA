package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive payment data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class PaymentRequest {
    /**
     * Selected payment mode used to process the transaction.
     */
    private String paymentType;
    /**
     * UPI identifier used for UPI-based payment.
     */
    private String upiId;
    /**
     * Card number supplied for card-based payment.
     */
    private String cardNumber;
    /**
     * Name of the card holder.
     */
    private String cardHolderName;
    /**
     * Name of the bank used for bank payment.
     */
    private String bankName;
    /**
     * Bank account number associated with the payment or profile.
     */
    private String accountNumber;

    /**
     * Creates a PaymentRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public PaymentRequest() {}

    /**
     * Returns the payment type.
     * @return selected payment mode used to process the transaction.
     */
    public String getPaymentType() { return paymentType; }
    /**
     * Updates the payment type carried by this DTO.
     * @param paymentType selected payment mode used to process the transaction.
     */
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    /**
     * Returns the upi id.
     * @return uPI identifier used for UPI-based payment.
     */
    public String getUpiId() { return upiId; }
    /**
     * Updates the upi id carried by this DTO.
     * @param upiId uPI identifier used for UPI-based payment.
     */
    public void setUpiId(String upiId) { this.upiId = upiId; }
    /**
     * Returns the card number.
     * @return card number supplied for card-based payment.
     */
    public String getCardNumber() { return cardNumber; }
    /**
     * Updates the card number carried by this DTO.
     * @param cardNumber card number supplied for card-based payment.
     */
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    /**
     * Returns the card holder name.
     * @return name of the card holder.
     */
    public String getCardHolderName() { return cardHolderName; }
    /**
     * Updates the card holder name carried by this DTO.
     * @param cardHolderName name of the card holder.
     */
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
    /**
     * Returns the bank name.
     * @return name of the bank used for bank payment.
     */
    public String getBankName() { return bankName; }
    /**
     * Updates the bank name carried by this DTO.
     * @param bankName name of the bank used for bank payment.
     */
    public void setBankName(String bankName) { this.bankName = bankName; }
    /**
     * Returns the account number.
     * @return bank account number associated with the payment or profile.
     */
    public String getAccountNumber() { return accountNumber; }
    /**
     * Updates the account number carried by this DTO.
     * @param accountNumber bank account number associated with the payment or profile.
     */
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}
