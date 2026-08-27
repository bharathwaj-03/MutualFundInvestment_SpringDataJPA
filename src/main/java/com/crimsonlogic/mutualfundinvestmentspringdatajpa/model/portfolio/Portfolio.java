package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents the investment portfolio owned by an investor and its collection of holdings.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "portfolio")
public class Portfolio {

    /**
     * Unique identifier of the investor portfolio.
     */
    @Id
    @Column(name = "portfolio_id", length = 20)
    private String portfolioId;

    /**
     * Investor associated with this record.
     *
     * This JPA one-to-one relationship links a single associated record.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private Investor investor;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Holding> holdings = new ArrayList<>();

    /**
     * Date of the most recent portfolio activity.
     */
    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Portfolio() {
    }

    /**
     * Returns the portfolio id.
     *
     * @return portfolio id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Updates the portfolio id.
     *
     * @param portfolioId new portfolio id value
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
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
     * Returns the holdings.
     *
     * @return holdings
     */
    public List<Holding> getHoldings() {
        return holdings;
    }

    /**
     * Updates the holdings.
     *
     * @param holdings new holdings value
     */
    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }

    /**
     * Returns the last activity date.
     *
     * @return last activity date
     */
    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     * Updates the last activity date.
     *
     * @param lastActivityDate new last activity date value
     */
    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    /**
     * Returns a readable representation of the Portfolio object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId='" + portfolioId + '\'' +
                ", investor=" + (investor != null ? investor.getUserId() : null) +
                ", lastActivityDate=" + lastActivityDate +
                '}';
    }

    /**
     * Compares this Portfolio with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return Objects.equals(portfolioId, portfolio.portfolioId);
    }

    /**
     * Returns a hash code consistent with the equality definition of Portfolio.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(portfolioId);
    }
}
