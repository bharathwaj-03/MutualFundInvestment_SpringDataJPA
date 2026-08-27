package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;

import java.util.List;

/**
 * Defines business operations for recording, retrieving, deleting, and summarizing transactions.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_TransactionService {

    /**
     * Persists a financial transaction.
     *
     * @param transaction transaction information
     * @return true when the operation succeeds; otherwise false
     */

    boolean addTransaction(Transaction transaction);

    /**
     * Retrieves a transaction by its unique transaction ID.
     *
     * @param transactionId transaction identifier
     * @return result of the business operation
     */

    Transaction getTransactionById(String transactionId);

    /**
     * Deletes the specified transaction when it exists.
     *
     * @param transactionId transaction identifier
     * @return true when the operation succeeds; otherwise false
     */

    boolean deleteTransaction(String transactionId);

    /**
     * Retrieves all financial transactions.
     *
     * @return list of matching records or response objects
     */

    List<Transaction> getAllTransactions();

    /**
     * Retrieves the most recently recorded transaction.
     *
     * @return result of the business operation
     */

    Transaction getLatestTransaction();

    /**
     * Calculates the total amount represented by all transactions.
     *
     * @return calculated numeric value
     */

    double getTotalTransactionAmount();

    /**
     * Returns the total number of stored transactions.
     *
     * @return calculated count or numeric value
     */

    long getTransactionCount();

    /**
     * Retrieves transactions ordered or filtered according to the repository amount query.
     *
     * @return list of matching records or response objects
     */

    List<Transaction> getTransactionsByAmount();
}