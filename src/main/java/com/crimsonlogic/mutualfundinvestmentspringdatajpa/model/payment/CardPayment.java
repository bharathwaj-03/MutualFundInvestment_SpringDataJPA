package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.UserDataValidation;

import java.util.Objects;

/**
 * Represents card-based payment details and card-number validation behavior.
 */
public class CardPayment implements Payable {

    /**
     * Card number used for card payment.
     */
    private String cardNumber;
    /**
     * Name of the card holder.
     */
    private String cardHolderName;


    /**
     * Validation function that accepts only supported card-number formats.
     */
    public UserDataValidation cardNumberValidate =
            str -> {

                boolean isValid =
                        str != null &&
                                str.matches(
                                        "^\\d{16,19}$"
                                );

                if (!isValid) {

                    throw new UserDataValidationException(
                            "Card number must contain 16 to 19 digits."
                    );
                }

                return str;
            };


    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public CardPayment() {
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
                "Card Payment of "
                        + amount
                        + " processed successfully"
        );
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
    public void setCardNumber(
            String cardNumber)
            throws UserDataValidationException {

        this.cardNumber =
                cardNumberValidate.validate(
                        cardNumber
                );
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
    public void setCardHolderName(
            String cardHolderName) {

        this.cardHolderName =
                cardHolderName;
    }


    /**
     * Returns a readable representation of the CardPayment object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {

        return "CardPayment{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                '}';
    }


    /**
     * Compares this CardPayment with another object for logical equality.
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

        CardPayment that =
                (CardPayment) o;

        return Objects.equals(
                cardNumber,
                that.cardNumber
        )
                &&
                Objects.equals(
                        cardHolderName,
                        that.cardHolderName
                );
    }


    /**
     * Returns a hash code consistent with the equality definition of CardPayment.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {

        return Objects.hash(
                cardNumber,
                cardHolderName
        );
    }
}