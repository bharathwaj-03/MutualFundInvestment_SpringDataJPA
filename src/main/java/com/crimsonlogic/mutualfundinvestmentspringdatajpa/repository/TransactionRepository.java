package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for transaction persistence and chronological transaction lookups.
 *
 * Spring Data JPA derives the filtering and ordering queries from method names.
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    /**
     * Finds an investor's transactions with the most recent transaction first.
     *
     * @param userId investor user ID
     * @return investor transactions in descending date-time order
     */
    List<Transaction> findByInvestor_UserIdOrderByTransactionDateTimeDesc(String userId);

    /**
     * Finds transactions for a mutual fund with the most recent first.
     *
     * @param fundId mutual fund ID
     * @return fund transactions in descending date-time order
     */
    List<Transaction> findByMutualFund_FundIdOrderByTransactionDateTimeDesc(String fundId);

    /**
     * Returns all transactions with the most recent transaction first.
     *
     * @return all transactions in descending date-time order
     */
    List<Transaction> findAllByOrderByTransactionDateTimeDesc();

    /**
     * Returns the most recently recorded transaction.
     *
     * @return latest transaction when one exists
     */
    Optional<Transaction> findTopByOrderByTransactionDateTimeDesc();
}
