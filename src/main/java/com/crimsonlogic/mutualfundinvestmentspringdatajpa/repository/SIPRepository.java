package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SIP persistence and SIP retrieval operations.
 *
 * Custom queries eagerly load investor and mutual fund relationships and
 * provide ordering appropriate for investor and administrator views.
 */
public interface SIPRepository
        extends JpaRepository<SIP, String> {

    /**
     * Finds one SIP and eagerly loads its investor and mutual fund.
     *
     * @param sipId SIP ID to search for
     * @return complete SIP when present
     */
    @Query(
            "SELECT s " +
                    "FROM SIP s " +
                    "JOIN FETCH s.investor " +
                    "JOIN FETCH s.mutualFund " +
                    "WHERE s.sipId = :sipId"
    )
    Optional<SIP> findByIdWithRelations(
            @Param("sipId")
            String sipId
    );

    /**
     * Finds SIPs belonging to an investor and eagerly loads required relationships.
     *
     * SIPs are ordered by next installment date so upcoming installments appear first.
     *
     * @param investorId investor ID used for filtering
     * @return investor SIPs ordered by next installment date
     */
    @Query(
            "SELECT s " +
                    "FROM SIP s " +
                    "JOIN FETCH s.investor i " +
                    "JOIN FETCH s.mutualFund " +
                    "WHERE i.userId = :investorId " +
                    "ORDER BY s.nextInstallmentDate ASC"
    )
    List<SIP> findByInvestorIdWithRelations(
            @Param("investorId")
            String investorId
    );

    /**
     * Returns all SIP records with investor and mutual fund data initialized.
     *
     * Results are ordered from most recent SIP activity to oldest.
     *
     * @return all SIP records with required relationships
     */
    @Query(
            "SELECT s " +
                    "FROM SIP s " +
                    "JOIN FETCH s.investor " +
                    "JOIN FETCH s.mutualFund " +
                    "ORDER BY s.activityDate DESC"
    )
    List<SIP> findAllWithRelations();
}