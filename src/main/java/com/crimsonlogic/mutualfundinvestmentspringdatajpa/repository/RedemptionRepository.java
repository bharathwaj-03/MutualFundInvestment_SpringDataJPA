package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for redemption persistence and retrieval operations.
 *
 * Custom queries eagerly fetch the investor, mutual fund, and optional
 * transaction required to represent a complete redemption record.
 */
public interface RedemptionRepository
        extends JpaRepository<Redemption, String> {

    /**
     * Finds one redemption and eagerly loads the investor, mutual fund, and optional transaction.
     *
     * @param redemptionId redemption ID to search for
     * @return complete redemption when present
     */
    @Query(
            "SELECT r " +
                    "FROM Redemption r " +
                    "JOIN FETCH r.investor i " +
                    "JOIN FETCH r.mutualFund mf " +
                    "LEFT JOIN FETCH r.transaction t " +
                    "WHERE r.redemptionId = :redemptionId"
    )
    Optional<Redemption> findByIdWithRelations(
            @Param("redemptionId")
            String redemptionId
    );

    /**
     * Finds an investor's redemptions with required relationships initialized.
     *
     * Results are returned with the most recent redemption activity first.
     *
     * @param investorId investor ID used for filtering
     * @return investor redemptions ordered by activity date
     */
    @Query(
            "SELECT r " +
                    "FROM Redemption r " +
                    "JOIN FETCH r.investor i " +
                    "JOIN FETCH r.mutualFund mf " +
                    "LEFT JOIN FETCH r.transaction t " +
                    "WHERE i.userId = :investorId " +
                    "ORDER BY r.activityDate DESC"
    )
    List<Redemption> findByInvestorIdWithRelations(
            @Param("investorId")
            String investorId
    );

    /**
     * Returns all redemptions with investor, mutual fund, and transaction data initialized.
     *
     * Results are ordered from most recent redemption activity to oldest.
     *
     * @return all redemptions with required relationships
     */
    @Query(
            "SELECT r " +
                    "FROM Redemption r " +
                    "JOIN FETCH r.investor " +
                    "JOIN FETCH r.mutualFund " +
                    "LEFT JOIN FETCH r.transaction " +
                    "ORDER BY r.activityDate DESC"
    )
    List<Redemption> findAllWithRelations();


    /**
     * Returns redemptions belonging to investors with the
     * requested active status.
     *
     * Investor, mutual fund, and transaction relationships
     * are eagerly loaded for safe response conversion.
     *
     * @param active investor account status
     * @return matching redemption records ordered by activity date
     */
    @Query(
            "SELECT r " +
                    "FROM Redemption r " +
                    "JOIN FETCH r.investor i " +
                    "JOIN FETCH r.mutualFund mf " +
                    "LEFT JOIN FETCH r.transaction t " +
                    "WHERE i.active = :active " +
                    "ORDER BY r.activityDate DESC"
    )
    List<Redemption>
    findByInvestorActiveWithRelations(
            @Param("active")
            boolean active
    );
}
