package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.UserDataValidation;

import java.util.Objects;

/**
 * Represents bank-account payment details and account-number validation behavior.
 */
public class BankPayment implements Payable {

    /**
     * Name of the bank used for payment.
     */
    private String bankName;
    /**
     * Bank account number associated with the record.
     */
    private String accountNumber;


    /**
     * Validation function that accepts only supported bank-account number formats.
     */
    public UserDataValidation accountNumberValidate =
            str -> {

                boolean isValid =
                        str != null &&
                                str.matches(
                                        "^\\d{9,18}$"
                                );

                if (!isValid) {

                    throw new UserDataValidationException(
                            "Account number must contain 9 to 18 digits."
                    );
                }

                return str;
            };


    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public BankPayment() {
    }


    /**
     * Processes a payment using this payment method.
     *
     * @param amount amount supplied to the operation
     */
    @Override
    public void processPayment(
            double amount) {

        System.out.println(
                "Bank Payment of "
                        + amount
                        + " processed successfully"
        );
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
    public void setBankName(
            String bankName) {

        this.bankName =
                bankName;
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
    public void setAccountNumber(
            String accountNumber)
            throws UserDataValidationException {

        this.accountNumber =
                accountNumberValidate.validate(
                        accountNumber
                );
    }


    /**
     * Returns a readable representation of the BankPayment object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {

        return "BankPayment{" +
                "bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }


    /**
     * Compares this BankPayment with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null ||
                getClass() != o.getClass())
            return false;

        BankPayment that =
                (BankPayment) o;

        return Objects.equals(
                bankName,
                that.bankName
        )
                &&
                Objects.equals(
                        accountNumber,
                        that.accountNumber
                );
    }


    /**
     * Returns a hash code consistent with the equality definition of BankPayment.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {

        return Objects.hash(
                bankName,
                accountNumber
        );
    }
}