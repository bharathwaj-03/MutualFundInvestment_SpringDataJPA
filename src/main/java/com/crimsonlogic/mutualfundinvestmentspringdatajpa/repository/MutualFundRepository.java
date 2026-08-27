package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for mutual fund persistence and sorted fund lookups.
 *
 * Spring Data JPA derives the required queries from the repository method names.
 */
public interface MutualFundRepository extends JpaRepository<MutualFund, String> {

    /**
     * Finds a mutual fund by its fund name.
     *
     * @param fundName fund name to search for
     * @return matching mutual fund when present
     */
    Optional<MutualFund> findByFundName(String fundName);

    /**
     * Finds a mutual fund by its unique fund code.
     *
     * @param fundCode fund code to search for
     * @return matching mutual fund when present
     */
    Optional<MutualFund> findByFundCode(String fundCode);

    /**
     * Finds funds in a category and orders them alphabetically by fund name.
     *
     * @param fundCategory category used to filter funds
     * @return matching funds ordered by fund name
     */
    List<MutualFund> findByFundCategoryOrderByFundNameAsc(String fundCategory);

    /**
     * Returns all mutual funds ordered first by category and then by fund name.
     *
     * @return all funds in consistent display order
     */
    List<MutualFund> findAllByOrderByFundCategoryAscFundNameAsc();
}
