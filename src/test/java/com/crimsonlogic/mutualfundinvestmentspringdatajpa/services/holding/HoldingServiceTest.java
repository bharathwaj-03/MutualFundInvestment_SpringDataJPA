package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.HoldingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HoldingServiceTest {

    @Mock
    private HoldingRepository holdingRepository;

    private AutoCloseable mocks;

    private HoldingService holdingService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        holdingService = new HoldingService(holdingRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void shouldCreateHolding() {
        Holding holding = new Holding();
        holding.setHoldingId("HLD001");

        assertTrue(holdingService.createHolding(holding));

        verify(holdingRepository).save(holding);
    }

    @Test
    void shouldRejectNullHolding() {
        assertFalse(holdingService.createHolding(null));
        verifyNoInteractions(holdingRepository);
    }

    @Test
    void shouldGetHoldingByIdWithRelations() {
        Holding holding = new Holding();
        holding.setHoldingId("HLD001");

        when(holdingRepository.findByIdWithRelations("HLD001"))
                .thenReturn(Optional.of(holding));

        assertSame(holding, holdingService.getHoldingById("HLD001"));
    }

    @Test
    void shouldThrowWhenHoldingDoesNotExist() {
        when(holdingRepository.findByIdWithRelations("HLD404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> holdingService.getHoldingById("HLD404")
        );
    }

    @Test
    void shouldReturnInvestorHoldingsWithRelations() {
        List<Holding> expected = List.of(new Holding(), new Holding());

        when(holdingRepository.findByInvestorIdWithRelations("INV001"))
                .thenReturn(expected);

        assertEquals(expected, holdingService.getHoldingsByInvestor("INV001"));
    }

    @Test
    void shouldDeleteExistingHolding() {
        when(holdingRepository.existsById("HLD001"))
                .thenReturn(true);

        assertTrue(holdingService.deleteHolding("HLD001"));

        verify(holdingRepository).deleteById("HLD001");
    }

    @Test
    void shouldNotDeleteMissingHolding() {
        when(holdingRepository.existsById("HLD404"))
                .thenReturn(false);

        assertFalse(holdingService.deleteHolding("HLD404"));

        verify(holdingRepository, never()).deleteById(anyString());
    }
}
