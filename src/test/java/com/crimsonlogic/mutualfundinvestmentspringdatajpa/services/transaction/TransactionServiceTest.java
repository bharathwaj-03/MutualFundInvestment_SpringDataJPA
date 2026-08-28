package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.BuyTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private AutoCloseable mocks;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    private Transaction transaction(String id, double amount) {
        Transaction transaction = new BuyTransaction();
        transaction.setTransactionId(id);
        transaction.setAmount(amount);
        return transaction;
    }

    @Test
    void shouldAddTransaction() {
        Transaction transaction = transaction("TXN001", 1000);

        assertTrue(transactionService.addTransaction(transaction));

        verify(transactionRepository).save(transaction);
    }

    @Test
    void shouldRejectNullTransaction() {
        assertFalse(transactionService.addTransaction(null));
    }

    @Test
    void shouldReturnTransactionById() {
        Transaction transaction = transaction("TXN001", 1000);

        when(transactionRepository.findById("TXN001"))
                .thenReturn(Optional.of(transaction));

        assertSame(
                transaction,
                transactionService.getTransactionById("TXN001")
        );
    }

    @Test
    void shouldThrowForMissingTransaction() {
        when(transactionRepository.findById("TXN404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.getTransactionById("TXN404")
        );
    }

    @Test
    void shouldCalculateTotalTransactionAmount() {
        when(transactionRepository.findAll())
                .thenReturn(List.of(
                        transaction("TXN1", 1000),
                        transaction("TXN2", 2500)
                ));

        assertEquals(
                3500,
                transactionService.getTotalTransactionAmount()
        );
    }

    @Test
    void shouldSortTransactionsByAmountAscending() {
        Transaction high = transaction("TXN2", 5000);
        Transaction low = transaction("TXN1", 1000);

        when(transactionRepository.findAll())
                .thenReturn(List.of(high, low));

        List<Transaction> result =
                transactionService.getTransactionsByAmount();

        assertEquals(1000, result.get(0).getAmount());
        assertEquals(5000, result.get(1).getAmount());
    }
}
