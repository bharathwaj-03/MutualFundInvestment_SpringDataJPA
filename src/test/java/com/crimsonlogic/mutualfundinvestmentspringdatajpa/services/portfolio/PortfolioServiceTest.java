package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.PortfolioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private InvestorRepository investorRepository;

    private AutoCloseable mocks;

    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        portfolioService =
                new PortfolioService(portfolioRepository, investorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void shouldReturnExistingPortfolioInsteadOfCreatingDuplicate() {
        Portfolio existing = new Portfolio();

        when(portfolioRepository.findByInvestor_UserId("INV001"))
                .thenReturn(Optional.of(existing));

        assertSame(existing, portfolioService.createPortfolio("INV001"));

        verify(portfolioRepository, never()).save(any());
    }

    @Test
    void shouldCreatePortfolioForExistingInvestor() {
        Investor investor = new Investor();
        investor.setUserId("INV001");

        when(portfolioRepository.findByInvestor_UserId("INV001"))
                .thenReturn(Optional.empty());

        when(investorRepository.findById("INV001"))
                .thenReturn(Optional.of(investor));

        Portfolio result = portfolioService.createPortfolio("INV001");

        assertNotNull(result);
        assertSame(investor, result.getInvestor());
        assertNotNull(result.getPortfolioId());

        verify(portfolioRepository).save(result);
    }

    @Test
    void shouldReturnNullWhenInvestorDoesNotExistDuringPortfolioCreation() {
        when(portfolioRepository.findByInvestor_UserId("INV404"))
                .thenReturn(Optional.empty());

        when(investorRepository.findById("INV404"))
                .thenReturn(Optional.empty());

        assertNull(portfolioService.createPortfolio("INV404"));
    }

    @Test
    void shouldGetPortfolioWithInvestorRelation() {
        Portfolio portfolio = new Portfolio();

        when(portfolioRepository.findByInvestorIdWithInvestor("INV001"))
                .thenReturn(Optional.of(portfolio));

        assertSame(portfolio, portfolioService.getPortfolio("INV001"));
    }

    @Test
    void shouldThrowWhenPortfolioDoesNotExist() {
        when(portfolioRepository.findByInvestorIdWithInvestor("INV404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioService.getPortfolio("INV404")
        );
    }

    @Test
    void shouldCalculatePortfolioValue() {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId("PORT001");

        when(portfolioRepository.findByInvestor_UserId("INV001"))
                .thenReturn(Optional.of(portfolio));

        when(portfolioRepository.calculatePortfolioValue("PORT001"))
                .thenReturn(42000.0);

        assertEquals(
                42000.0,
                portfolioService.calculatePortfolioValue("INV001")
        );
    }
}
