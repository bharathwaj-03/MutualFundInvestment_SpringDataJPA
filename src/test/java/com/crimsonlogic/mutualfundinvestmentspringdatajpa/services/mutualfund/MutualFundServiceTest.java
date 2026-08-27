package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.MutualFundRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NAVHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutualFundServiceTest {

    @Mock
    private MutualFundRepository mutualFundRepository;

    @Mock
    private NAVHistoryRepository navHistoryRepository;

    private MutualFundService mutualFundService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mutualFundService =
                new MutualFundService(mutualFundRepository, navHistoryRepository);
    }

    private MutualFund validFund() {
        MutualFund fund = new EquityFund();
        fund.setFundId("FND001");
        fund.setFundName("Test Equity Fund");
        fund.setFundCategory("Equity Fund");
        fund.setNav(500);
        fund.setMinimumInvestment(500);
        fund.setSipGainPerYear(10);
        fund.setLumpSumGainPerYear(12);
        return fund;
    }

    @Test
    void shouldAddValidFund() {
        MutualFund fund = validFund();

        mutualFundService.addFund(fund);

        verify(mutualFundRepository).save(fund);
    }

    @Test
    void shouldRejectNullFund() {
        assertThrows(
                InvalidRequestException.class,
                () -> mutualFundService.addFund(null)
        );
    }

    @Test
    void shouldRejectFundWithInvalidNav() {
        MutualFund fund = validFund();
        fund.setNav(0);

        assertThrows(
                InvalidRequestException.class,
                () -> mutualFundService.addFund(fund)
        );
    }

    @Test
    void shouldReturnFundById() {
        MutualFund fund = validFund();

        when(mutualFundRepository.findById("FND001"))
                .thenReturn(Optional.of(fund));

        assertSame(fund, mutualFundService.getFundById("FND001"));
    }

    @Test
    void shouldThrowWhenFundNotFound() {
        when(mutualFundRepository.findById("FND404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> mutualFundService.getFundById("FND404")
        );
    }

    @Test
    void shouldGetFundsByCategory() {
        List<MutualFund> expected = List.of(validFund());

        when(mutualFundRepository
                .findByFundCategoryOrderByFundNameAsc("Equity Fund"))
                .thenReturn(expected);

        assertEquals(
                expected,
                mutualFundService.getFundsByCategory("Equity Fund")
        );
    }

    @Test
    void shouldDeleteExistingFund() {
        MutualFund fund = validFund();

        when(mutualFundRepository.findById("FND001"))
                .thenReturn(Optional.of(fund));

        mutualFundService.deleteFund("FND001");

        verify(mutualFundRepository).deleteById("FND001");
    }
}
