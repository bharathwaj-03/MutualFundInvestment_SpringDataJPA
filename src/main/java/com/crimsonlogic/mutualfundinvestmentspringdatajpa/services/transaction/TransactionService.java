package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.TransactionRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;

import java.util.Comparator;
import java.util.List;


/**
 * Provides persistence, retrieval, deletion, summary, and reporting operations for financial transactions.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class TransactionService
        implements I_TransactionService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final TransactionRepository transactionRepository;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param transactionRepository transactionRepository dependency used by the service
     */


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Persists a financial transaction.
     *
     * @param transaction transaction information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean addTransaction(
            Transaction transaction) {

        try {

            if (transaction == null) {
                return false;
            }

            transactionRepository.save(transaction);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves a transaction by its unique transaction ID.
     *
     * @param transactionId transaction identifier
     * @return result of the business operation
     */
    @Override
    public Transaction getTransactionById(
            String transactionId) {

        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaction not found with id: " + transactionId
                ));
    }

    /**
     * Deletes the specified transaction when it exists.
     *
     * @param transactionId transaction identifier
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean deleteTransaction(
            String transactionId) {

        try {

            Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

            if (transaction == null) {
                return false;
            }

            transactionRepository.deleteById(transactionId);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves all financial transactions.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAllByOrderByTransactionDateTimeDesc();
    }

    /**
     * Retrieves the most recently recorded transaction.
     *
     * @return result of the business operation
     */
    @Override
    public Transaction getLatestTransaction() {

        return transactionRepository.findTopByOrderByTransactionDateTimeDesc().orElse(null);
    }

    /**
     * Calculates the total amount represented by all transactions.
     *
     * @return calculated numeric value
     */
    @Override
    public double getTotalTransactionAmount() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        if (transactions == null) {
            return 0.0;
        }

        return transactions.stream()
                .mapToDouble(
                        Transaction::getAmount
                )
                .sum();
    }

    /**
     * Returns the total number of stored transactions.
     *
     * @return calculated count or numeric value
     */
    @Override
    public long getTransactionCount() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        if (transactions == null) {
            return 0;
        }

        return transactions.size();
    }

    /**
     * Retrieves transactions ordered or filtered according to the repository amount query.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<Transaction> getTransactionsByAmount() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        if (transactions == null) {
            return List.of();
        }

        return transactions.stream()
                .sorted(
                        Comparator.comparingDouble(
                                Transaction::getAmount
                        )
                )
                .toList();
    }
}