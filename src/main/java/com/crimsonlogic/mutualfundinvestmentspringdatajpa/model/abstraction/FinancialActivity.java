package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Mapped superclass that stores common information shared by financial activities.
 */
@MappedSuperclass
public abstract class FinancialActivity {

 
    /**
     * Identifier of the financial activity.
     */
    @Transient
    private long activityId;

    /**
     * Monetary amount associated with the activity or transaction.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Date on which the financial activity occurred.
     */
    @Column(name = "activity_date")
    private LocalDate activityDate;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public FinancialActivity() {
    }

    /**
     * Returns the activity id.
     *
     * @return activity id
     */
    public long getActivityId() {
        return activityId;
    }

    /**
     * Updates the activity id.
     *
     * @param activityId new activity id value
     */
    public void setActivityId(long activityId) {
        this.activityId = activityId;
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
     * Returns the activity date.
     *
     * @return activity date
     */
    public LocalDate getActivityDate() {
        return activityDate;
    }

    /**
     * Updates the activity date.
     *
     * @param activityDate new activity date value
     */
    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }
}
