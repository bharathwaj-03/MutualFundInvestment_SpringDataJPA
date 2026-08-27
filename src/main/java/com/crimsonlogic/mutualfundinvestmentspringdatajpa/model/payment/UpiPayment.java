package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.UserDataValidation;
import java.util.Objects;

/**
 * Represents UPI payment details and UPI-ID validation behavior.
 */
public class UpiPayment implements Payable {

    /**
     * UPI identifier used for the payment.
     */
    private String upiId;

    /**
     * Validation function that verifies and normalizes a UPI identifier.
     */
    public UserDataValidation upiValidate = str -> {
        boolean isValid = str != null
                && str.matches("^[a-zA-Z0-9._]{3,}@[a-zA-Z]{2,20}$");

        if (!isValid) {
            throw new UserDataValidationException(
                    "Please enter a valid UPI ID. Ex: bharath@okaxis");
        }

        return str.toLowerCase();
    };

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public UpiPayment() {
    }

    /**
     * Processes a payment using this payment method.
     *
     * @param amount amount supplied to the operation
     */
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI Payment of ₹" + amount);
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
    public void setUpiId(String upiId) throws UserDataValidationException {
        this.upiId = upiValidate.validate(upiId);
    }

    /**
     * Returns a readable representation of the UpiPayment object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "UpiPayment{" +
                "upiId='" + upiId + '\'' +
                '}';
    }

    /**
     * Compares this UpiPayment with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpiPayment that = (UpiPayment) o;
        return Objects.equals(upiId, that.upiId);
    }

    /**
     * Returns a hash code consistent with the equality definition of UpiPayment.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(upiId);
    }
}
