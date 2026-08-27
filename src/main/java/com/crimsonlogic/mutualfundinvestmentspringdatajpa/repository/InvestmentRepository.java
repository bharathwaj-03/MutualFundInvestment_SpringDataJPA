package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for investment persistence and retrieval operations.
 *
 * The custom queries fetch the associated investor and mutual fund together
 * with each investment so the service layer can safely use the complete record.
 */
public interface InvestmentRepository
        extends JpaRepository<Investment, String> {

    /**
     * Finds one investment and eagerly loads its investor and mutual fund.
     *
     * @param investmentId investment ID to search for
     * @return complete investment when present
     */
    @Query(
            "SELECT i " +
                    "FROM Investment i " +
                    "JOIN FETCH i.investor " +
                    "JOIN FETCH i.mutualFund " +
                    "WHERE i.investmentId = :investmentId"
    )
    Optional<Investment> findByIdWithRelations(
            @Param("investmentId")
            String investmentId
    );

    /**
     * Finds an investor's investments with investor and fund relationships initialized.
     *
     * Most recent investment activity is returned first.
     *
     * @param investorId investor ID used for filtering
     * @return investor investments ordered by activity date
     */
    @Query(
            "SELECT i " +
                    "FROM Investment i " +
                    "JOIN FETCH i.investor inv " +
                    "JOIN FETCH i.mutualFund " +
                    "WHERE inv.userId = :investorId " +
                    "ORDER BY i.activityDate DESC"
    )
    List<Investment> findByInvestorIdWithRelations(
            @Param("investorId")
            String investorId
    );

    /**
     * Returns all investments with investor and mutual fund relationships initialized.
     *
     * Results are ordered from most recent investment activity to oldest.
     *
     * @return all investments with required relationships
     */
    @Query(
            "SELECT i " +
                    "FROM Investment i " +
                    "JOIN FETCH i.investor " +
                    "JOIN FETCH i.mutualFund " +
                    "ORDER BY i.activityDate DESC"
    )
    List<Investment> findAllWithRelations();
}