package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for NAV history persistence and history retrieval.
 *
 * Custom queries fetch the related mutual fund together with each history
 * record to avoid lazy-loading problems when the data is returned by the API.
 */
public interface NAVHistoryRepository
        extends JpaRepository<NAVHistory, String> {

    /**
     * Returns all NAV history records with the related mutual fund initialized.
     *
     * The newest NAV changes are returned first.
     *
     * @return all NAV history records ordered by change date
     */
    @Query("""
            SELECT nh
            FROM NAVHistory nh
            JOIN FETCH nh.mutualFund
            ORDER BY nh.changeDate DESC
            """)
    List<NAVHistory> findAllWithMutualFund();

    /**
     * Returns NAV history for one mutual fund with the fund relationship initialized.
     *
     * @param fundId mutual fund ID used to filter history
     * @return NAV history for the fund ordered by change date
     */
    @Query("""
            SELECT nh
            FROM NAVHistory nh
            JOIN FETCH nh.mutualFund mf
            WHERE mf.fundId = :fundId
            ORDER BY nh.changeDate DESC
            """)
    List<NAVHistory> findByFundIdWithMutualFund(
            @Param("fundId")
            String fundId
    );
}