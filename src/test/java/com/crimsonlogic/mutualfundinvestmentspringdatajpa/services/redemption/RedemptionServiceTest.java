package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.redemption;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InsufficientUnitsException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.RedemptionRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedemptionServiceTest {

    @Mock
    private RedemptionRepository redemptionRepository;
    @Mock
    private I_HoldingService holdingService;
    @Mock
    private I_MutualFundService mutualFundService;
    @Mock
    private I_PortfolioService portfolioService;
    @Mock
    private I_TransactionService transactionService;

    private RedemptionService redemptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        redemptionService = new RedemptionService(
                redemptionRepository,
                holdingService,
                mutualFundService,
                portfolioService,
                transactionService
        );
    }

    private Holding holding(double units) {
        Investor investor = new Investor();
        investor.setUserId("INV001");

        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId("PORT001");
        portfolio.setInvestor(investor);

        MutualFund fund = new EquityFund();
        fund.setFundId("FND001");
        fund.setNav(590);

        Holding holding = new Holding();
        holding.setHoldingId("HLD001");
        holding.setPortfolio(portfolio);
        holding.setMutualFund(fund);
        holding.setUnitsOwned(units);
        holding.setInvestedAmount(5000);
        holding.setAverageNav(500);

        return holding;
    }

    @Test
    void shouldCalculateRedemptionPreview() throws InsufficientUnitsException {
        Holding holding = holding(10);

        MutualFund fund = new EquityFund();
        fund.setFundId("FND001");
        fund.setFundName("Equity Fund");
        fund.setNav(590);

        when(holdingService.getHoldingById("HLD001"))
                .thenReturn(holding);

        when(mutualFundService.getFundById("FND001"))
                .thenReturn(fund);

        Redemption result =
                redemptionService.calculateRedemption(
                        "INV001",
                        "HLD001",
                        1
                );

        assertEquals(590, result.getGrossAmount());
        assertEquals(5, result.getBrokerageCharges());
        assertEquals(585, result.getAmountReceived());
        assertNull(result.getRedemptionId());
    }

    @Test
    void shouldRejectZeroUnits() {
        assertThrows(
                InvalidRequestException.class,
                () -> redemptionService.calculateRedemption(
                        "INV001",
                        "HLD001",
                        0
                )
        );
    }

    @Test
    void shouldRejectHoldingOwnedByDifferentInvestor() {
        Holding holding = holding(10);
        holding.getPortfolio().getInvestor().setUserId("INV999");

        when(holdingService.getHoldingById("HLD001"))
                .thenReturn(holding);

        assertThrows(
                InvalidRequestException.class,
                () -> redemptionService.calculateRedemption(
                        "INV001",
                        "HLD001",
                        1
                )
        );
    }

    @Test
    void shouldRejectUnitsGreaterThanHoldingUnits() {
        Holding holding = holding(2);

        when(holdingService.getHoldingById("HLD001"))
                .thenReturn(holding);

        assertThrows(
                InsufficientUnitsException.class,
                () -> redemptionService.calculateRedemption(
                        "INV001",
                        "HLD001",
                        5
                )
        );
    }

    @Test
    void shouldGetInvestorRedemptionsWithRelations() {
        List<Redemption> expected = List.of(new Redemption());

        when(redemptionRepository
                .findByInvestorIdWithRelations("INV001"))
                .thenReturn(expected);

        assertEquals(
                expected,
                redemptionService.getRedemptionsByUser("INV001")
        );
    }

    @Test
    void shouldGetRedemptionByIdWithRelations() {
        Redemption redemption = new Redemption();

        when(redemptionRepository
                .findByIdWithRelations("RED001"))
                .thenReturn(Optional.of(redemption));

        assertSame(
                redemption,
                redemptionService.getRedemptionById("RED001")
        );
    }
}
