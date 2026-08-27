package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for portfolio holding persistence and reporting queries.
 *
 * Custom JPQL queries eagerly load related portfolio, investor, and mutual
 * fund data where those relationships are required outside the persistence session.
 */
public interface HoldingRepository
        extends JpaRepository<Holding, String> {

    /**
     * Finds one holding and eagerly loads its portfolio, investor, and mutual fund.
     *
     * @param holdingId holding ID to search for
     * @return complete holding when present
     */
    @Query(
            "SELECT h " +
                    "FROM Holding h " +
                    "JOIN FETCH h.portfolio p " +
                    "JOIN FETCH p.investor i " +
                    "JOIN FETCH h.mutualFund mf " +
                    "WHERE h.holdingId = :holdingId"
    )
    Optional<Holding> findByIdWithRelations(
            @Param("holdingId")
            String holdingId
    );

    /**
     * Finds all holdings owned by an investor and eagerly loads required relationships.
     *
     * Results are ordered by mutual fund name for consistent presentation.
     *
     * @param investorId investor ID used to filter holdings
     * @return investor holdings with related data initialized
     */
    @Query(
            "SELECT h " +
                    "FROM Holding h " +
                    "JOIN FETCH h.portfolio p " +
                    "JOIN FETCH p.investor i " +
                    "JOIN FETCH h.mutualFund mf " +
                    "WHERE i.userId = :investorId " +
                    "ORDER BY mf.fundName ASC"
    )
    List<Holding> findByInvestorIdWithRelations(
            @Param("investorId")
            String investorId
    );

    /**
     * Finds holdings for a portfolio and eagerly loads investor and mutual fund details.
     *
     * @param portfolioId portfolio ID used to filter holdings
     * @return holdings belonging to the portfolio
     */
    @Query(
            "SELECT h " +
                    "FROM Holding h " +
                    "JOIN FETCH h.portfolio p " +
                    "JOIN FETCH p.investor i " +
                    "JOIN FETCH h.mutualFund mf " +
                    "WHERE p.portfolioId = :portfolioId"
    )
    List<Holding> findByPortfolioIdWithRelations(
            @Param("portfolioId")
            String portfolioId
    );

    /**
     * Finds the holding for a specific portfolio and mutual fund combination.
     *
     * @param portfolioId portfolio ID
     * @param fundId mutual fund ID
     * @return matching holding when present
     */
    @Query(
            "SELECT h " +
                    "FROM Holding h " +
                    "JOIN FETCH h.portfolio p " +
                    "JOIN FETCH p.investor i " +
                    "JOIN FETCH h.mutualFund mf " +
                    "WHERE p.portfolioId = :portfolioId " +
                    "AND mf.fundId = :fundId"
    )
    Optional<Holding> findByPortfolioAndFundWithRelations(
            @Param("portfolioId")
            String portfolioId,

            @Param("fundId")
            String fundId
    );
    /**
     * Returns all holdings with portfolio, investor, and mutual fund relationships initialized.
     *
     * @return all holdings ordered by holding ID
     */
    @Query(
            "SELECT h " +
                    "FROM Holding h " +
                    "JOIN FETCH h.portfolio p " +
                    "JOIN FETCH p.investor " +
                    "JOIN FETCH h.mutualFund " +
                    "ORDER BY h.holdingId ASC"
    )
    List<Holding> findAllWithRelations();

    /**
     * Aggregates investment performance by mutual fund category.
     *
     * The query returns the fund category, total invested amount, and current
     * value calculated as units owned multiplied by the current NAV.
     *
     * @return one Object array per fund category containing the aggregated values
     */
    @Query("""
       SELECT
           h.mutualFund.fundCategory,
           COALESCE(SUM(h.investedAmount), 0),
           COALESCE(
               SUM(
                   h.unitsOwned
                   * h.mutualFund.nav
               ),
               0
           )
       FROM Holding h
       GROUP BY h.mutualFund.fundCategory
       ORDER BY h.mutualFund.fundCategory
       """)
    List<Object[]>
    getFundCategoryPerformance();

    /**
     * Calculates current portfolio value for each investor and fund category.
     *
     * Only investors matching the requested active status are included.
     *
     * @param active investor account status used for filtering
     * @return aggregated investor/category value rows
     */
    @Query("""
       SELECT
           i.userId,
           i.name,
           mf.fundCategory,
           COALESCE(
               SUM(
                   h.unitsOwned * mf.nav
               ),
               0
           )
       FROM Holding h
       JOIN h.portfolio p
       JOIN p.investor i
       JOIN h.mutualFund mf
       WHERE i.active = :active
       GROUP BY
           i.userId,
           i.name,
           mf.fundCategory
       ORDER BY
           i.userId
       """)
    List<Object[]>
    getInvestorPortfolioCategoryValues(
            @Param("active")
            boolean active
    );
}