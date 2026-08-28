package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.validation.ValidPayment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidPayment
public class PaymentRequest {

    /**
     * Allowed values:
     * UPI, CARD, BANK
     */
    @NotBlank(
            message = "Payment type is required."
    )
    @Pattern(
            regexp = "^(UPI|CARD|BANK)$",
            message = "Payment type must be UPI, CARD or BANK."
    )
    private String paymentType;


    /**
     * Required only when paymentType = UPI.
     *
     * Examples:
     * bharath@okaxis
     * user@ybl
     * user@paytm
     */
    @Pattern(
            regexp = "^$|^[A-Za-z0-9._-]{2,256}@[A-Za-z][A-Za-z0-9.-]{1,63}$",
            message = "Please enter a valid UPI ID. Ex: bharath@okaxis"
    )
    private String upiId;


    /**
     * Required only when paymentType = CARD.
     *
     * 16 digits = card number
     * 19 digits = 16 digit card number + 3 digit CVV
     */
    @Pattern(
            regexp = "^$|^(?:[0-9]{16}|[0-9]{19})$",
            message = "Card number must contain exactly 16 digits or 19 digits including CVV."
    )
    private String cardNumber;


    /**
     * Required only when paymentType = CARD.
     */


    @Size(
            min = 3,
            max = 50,
            message =
                    "Name must contain 3 to 50 characters."
    )
    @Pattern.List({

            @Pattern(
                    regexp =
                            "^[A-Za-z]+(?: [A-Za-z]+)*$",
                    message =
                            "Name should contain only alphabets and spaces."
            ),

            @Pattern(
                    regexp =
                            "(?i)^(?!.*([a-z])\\1\\1).*$",
                    message =
                            "Name should not contain the same character 3 times continuously."
            )
    })
    private String cardHolderName;


    /**
     * Required only when paymentType = BANK.
     */
    @Pattern(
            regexp = "^$|^[A-Za-z][A-Za-z ]{1,49}$",
            message = "Bank name must contain only alphabetic characters."
    )
    private String bankName;


    /**
     * Required only when paymentType = BANK.
     *
     * 9 to 18 digits.
     */
    @Pattern(
            regexp = "^$|^[0-9]{9,18}$",
            message = "Account number must contain 9 to 18 digits."
    )
    private String accountNumber;


    public PaymentRequest() {
    }


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(
            String paymentType) {
        this.paymentType = paymentType;
    }


    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(
            String upiId) {
        this.upiId = upiId;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(
            String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(
            String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(
            String bankName) {
        this.bankName = bankName;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(
            String accountNumber) {
        this.accountNumber = accountNumber;
    }
}