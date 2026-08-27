package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.User;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Represents an investor account and its investment-related profile information.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "investor")
@PrimaryKeyJoinColumn(name = "user_id")
public class Investor extends User implements Comparable<Investor> {

    /**
     * PAN number associated with the investor.
     */
    @Column(name = "pan_number")
    private String panNumber;

    /**
     * Bank account number associated with the record.
     */
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * Date on which the investor account was registered.
     */
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    /**
     * Risk profile used to describe the investor's investment preference.
     */
    @Column(name = "risk_profile", length = 30)
    private String riskProfile;

    /**
     * Indicates whether the investor account is active.
     */
    @Column(name = "active")
    private boolean active;

    /**
     * Nominee associated with the investor account.
     *
     * This JPA one-to-one relationship links a single associated record.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nominee_id", unique = true)
    private Nominee nominee;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Investor() {
    }

    /**
     * Returns the pan number.
     *
     * @return pan number
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * Updates the pan number.
     *
     * @param panNumber new pan number value
     */
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
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
     * Returns the registration date.
     *
     * @return registration date
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Updates the registration date.
     *
     * @param registrationDate new registration date value
     */
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Returns the risk profile.
     *
     * @return risk profile
     */
    public String getRiskProfile() {
        return riskProfile;
    }

    /**
     * Updates the risk profile.
     *
     * @param riskProfile new risk profile value
     */
    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    /**
     * Returns whether the active condition is true.
     *
     * @return true when active is enabled; otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Updates the active.
     *
     * @param active new active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the nominee.
     *
     * @return nominee
     */
    public Nominee getNominee() {
        return nominee;
    }

    /**
     * Updates the nominee.
     *
     * @param nominee new nominee value
     */
    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

    /**
     * Compares this Investor with another instance to provide a consistent ordering.
     *
     * @param other other supplied to the operation
     *
     * @return result produced by the compare to operation
     */
    @Override
    public int compareTo(Investor other) {
        return this.getUserId().compareTo(other.getUserId());
    }

    /**
     * Returns a readable representation of the Investor object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Investor{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", riskProfile='" + riskProfile + '\'' +
                ", active=" + active +
                '}';
    }

    /**
     * Compares this Investor with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return active == investor.active
                && Objects.equals(panNumber, investor.panNumber)
                && Objects.equals(registrationDate, investor.registrationDate)
                && Objects.equals(riskProfile, investor.riskProfile);
    }

    /**
     * Returns a hash code consistent with the equality definition of Investor.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(panNumber, registrationDate, riskProfile, active);
    }
}
