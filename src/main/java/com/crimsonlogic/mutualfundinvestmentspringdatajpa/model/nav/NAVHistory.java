package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Stores an audit record of changes made to the Net Asset Value of a mutual fund.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "nav_history")
public class NAVHistory {

    /**
     * Unique identifier of the NAV history record.
     */
    @Id
    @Column(name = "history_id", length = 20)
    private String historyId;

    /**
     * Mutual fund associated with this record.
     *
     * Many records of this entity can reference the same associated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", nullable = false)
    private MutualFund mutualFund;

    /**
     * NAV value before the update.
     */
    @Column(name = "old_nav", nullable = false)
    private double oldNav;

    /**
     * NAV value after the update.
     */
    @Column(name = "new_nav", nullable = false)
    private double newNav;

    /**
     * Date on which the NAV was changed.
     */
    @Column(name = "change_date", nullable = false)
    private LocalDate changeDate;

    /**
     * Identifier of the administrator who changed the NAV.
     */
    // The current DB column is not declared as a foreign key, so it remains a String for now.
    @Column(name = "changed_by", length = 20)
    private String changedBy;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public NAVHistory() {
    }

    /**
     * Returns the history id.
     *
     * @return history id
     */
    public String getHistoryId() {
        return historyId;
    }

    /**
     * Updates the history id.
     *
     * @param historyId new history id value
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
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
     * Returns the old nav.
     *
     * @return old nav
     */
    public double getOldNav() {
        return oldNav;
    }

    /**
     * Updates the old nav.
     *
     * @param oldNav new old nav value
     */
    public void setOldNav(double oldNav) {
        this.oldNav = oldNav;
    }

    /**
     * Returns the new nav.
     *
     * @return new nav
     */
    public double getNewNav() {
        return newNav;
    }

    /**
     * Updates the new nav.
     *
     * @param newNav new new nav value
     */
    public void setNewNav(double newNav) {
        this.newNav = newNav;
    }

    /**
     * Returns the change date.
     *
     * @return change date
     */
    public LocalDate getChangeDate() {
        return changeDate;
    }

    /**
     * Updates the change date.
     *
     * @param changeDate new change date value
     */
    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * Returns the changed by.
     *
     * @return changed by
     */
    public String getChangedBy() {
        return changedBy;
    }

    /**
     * Updates the changed by.
     *
     * @param changedBy new changed by value
     */
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
