package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.navhistory;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav.NAVHistory;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NAVHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NAVHistoryServiceTest {

    @Mock
    private NAVHistoryRepository navHistoryRepository;

    private NAVHistoryService navHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        navHistoryService = new NAVHistoryService(navHistoryRepository);
    }

    @Test
    void shouldGetHistoryByFundId() {
        List<NAVHistory> expected = List.of(new NAVHistory());

        when(navHistoryRepository.findByFundIdWithMutualFund("FND001"))
                .thenReturn(expected);

        assertEquals(
                expected,
                navHistoryService.getNAVHistoryByFundId("FND001")
        );
    }

    @Test
    void shouldRejectEmptyFundId() {
        assertThrows(
                InvalidRequestException.class,
                () -> navHistoryService.getNAVHistoryByFundId(" ")
        );
    }

    @Test
    void shouldGetAllNavHistory() {
        List<NAVHistory> expected = List.of(new NAVHistory());

        when(navHistoryRepository.findAllWithMutualFund())
                .thenReturn(expected);

        assertEquals(expected, navHistoryService.getAllNAVHistory());
    }

    @Test
    void shouldWrapUnexpectedRepositoryFailure() {
        when(navHistoryRepository.findAllWithMutualFund())
                .thenThrow(new RuntimeException("DB down"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> navHistoryService.getAllNAVHistory()
        );

        assertEquals("Unable to retrieve NAV history.", ex.getMessage());
    }
}
