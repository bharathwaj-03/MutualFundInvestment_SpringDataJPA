package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository for investor persistence, account-status operations, and profile lookups.
 *
 * Spring Data derives simple lookup queries from method names, while custom
 * JPQL is used for status updates and eager loading of nominee information.
 */
public interface InvestorRepository extends JpaRepository<Investor, String> {

    /**
     * Finds an investor by user ID and role.
     *
     * @param userId investor user ID
     * @param userRole expected user role
     * @return matching investor when present
     */
    Optional<Investor> findByUserIdAndUserRole(String userId, String userRole);

    /**
     * Finds an investor by the stored PAN value.
     *
     * @param panNumber PAN value used for lookup
     * @return matching investor when present
     */
    Optional<Investor> findByPanNumber(String panNumber);

    /**
     * Finds investors according to their active account status.
     *
     * @param active true for active investors and false for inactive investors
     * @return investors matching the requested status
     */
    List<Investor> findByActive(boolean active);

    @Modifying
    /**
     * Marks an investor account as inactive without deleting the investor record.
     *
     * @param investorId ID of the investor to deactivate
     * @return number of database rows updated
     */
    @Query("""
           UPDATE Investor i
           SET i.active = false
           WHERE i.userId = :investorId
           """)
    int deactivateInvestor(
            @Param("investorId")
            String investorId
    );

    /**
     * Returns inactive investors together with their nominee details.
     *
     * LEFT JOIN FETCH is used so nominee information is initialized before the
     * persistence session closes.
     *
     * @return inactive investors ordered by user ID
     */
    @Query("""
       SELECT i
       FROM Investor i
       LEFT JOIN FETCH i.nominee
       WHERE i.active = false
       ORDER BY i.userId
       """)
    List<Investor> findInactiveInvestorsWithNominee();

    /**
     * Finds one investor and eagerly loads the associated nominee.
     *
     * @param investorId investor ID to search for
     * @return investor with nominee details when present
     */
    @Query("""
       SELECT i
       FROM Investor i
       LEFT JOIN FETCH i.nominee
       WHERE i.userId = :investorId
       """)
    Optional<Investor>
    findByIdWithNominee(
            @Param("investorId")
            String investorId
    );
}

