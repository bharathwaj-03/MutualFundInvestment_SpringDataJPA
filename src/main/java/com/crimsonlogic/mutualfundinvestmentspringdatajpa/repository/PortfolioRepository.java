package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for portfolio persistence, investor-based lookup, and portfolio valuation.
 *
 * Custom JPQL is used when related investor data or an aggregate portfolio
 * value must be retrieved directly from the database.
 */
public interface PortfolioRepository
        extends JpaRepository<Portfolio, String> {
    /**
     * Finds the portfolio owned by an investor.
     *
     * This lookup is used when checking whether a portfolio already exists.
     *
     * @param userId investor user ID
     * @return investor portfolio when present
     */
    Optional<Portfolio>
    findByInvestor_UserId(
            String userId
    );

    /**
     * Finds an investor's portfolio and eagerly loads the investor relationship.
     *
     * @param investorId investor ID used to locate the portfolio
     * @return portfolio with investor data when present
     */
    @Query(
            "SELECT p " +
                    "FROM Portfolio p " +
                    "JOIN FETCH p.investor i " +
                    "WHERE i.userId = :investorId"
    )
    Optional<Portfolio>
    findByInvestorIdWithInvestor(
            @Param("investorId")
            String investorId
    );

    /**
     * Returns all portfolios ordered by portfolio ID.
     *
     * @return all portfolios in ascending ID order
     */
    List<Portfolio>
    findAllByOrderByPortfolioIdAsc();

    /**
     * Calculates the current monetary value of a portfolio.
     *
     * Each holding contributes units owned multiplied by the mutual fund
     * current NAV. COALESCE returns zero when the portfolio has no holdings.
     *
     * @param portfolioId portfolio ID whose value is calculated
     * @return current portfolio value
     */
    @Query(
            "SELECT COALESCE(" +
                    "SUM(h.unitsOwned * h.mutualFund.nav), 0) " +
                    "FROM Holding h " +
                    "WHERE h.portfolio.portfolioId = :portfolioId"
    )
    double calculatePortfolioValue(
            @Param("portfolioId")
            String portfolioId
    );
}