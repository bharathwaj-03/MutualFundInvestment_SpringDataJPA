package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestmentRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment.I_PaymentService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestmentServiceTest {

    @Mock
    private InvestmentRepository investmentRepository;
    @Mock
    private I_InvestorService investorService;
    @Mock
    private I_MutualFundService mutualFundService;
    @Mock
    private I_PaymentService paymentService;
    @Mock
    private I_TransactionService transactionService;
    @Mock
    private I_HoldingService holdingService;
    @Mock
    private I_PortfolioService portfolioService;

    private InvestmentService investmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        investmentService = new InvestmentService(
                investmentRepository,
                investorService,
                mutualFundService,
                paymentService,
                transactionService,
                holdingService,
                portfolioService
        );
    }

    @Test
    void shouldReturnValidationErrorForMissingFund() {
        Map<String, String> errors =
                investmentService.validateInvestment(
                        null,
                        1000,
                        5,
                        "UPI"
                );

        assertTrue(errors.containsKey("fundId"));
    }

    @Test
    void shouldReturnValidationErrorForInvalidAmount() {
        Map<String, String> errors =
                investmentService.validateInvestment(
                        "FND001",
                        0,
                        5,
                        "UPI"
                );

        assertTrue(errors.containsKey("amount"));
    }

    @Test
    void shouldGetInvestmentByIdWithRelations() {
        Investment investment = new Investment();

        when(investmentRepository.findByIdWithRelations("INVT001"))
                .thenReturn(Optional.of(investment));

        assertSame(
                investment,
                investmentService.getInvestmentById("INVT001")
        );
    }

    @Test
    void shouldThrowWhenInvestmentDoesNotExist() {
        when(investmentRepository.findByIdWithRelations("INVT404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> investmentService.getInvestmentById("INVT404")
        );
    }

    @Test
    void shouldGetInvestorInvestmentsWithRelations() {
        List<Investment> expected = List.of(new Investment());

        when(investmentRepository
                .findByInvestorIdWithRelations("INV001"))
                .thenReturn(expected);

        assertEquals(
                expected,
                investmentService.getInvestmentsByUser("INV001")
        );
    }

    @Test
    void shouldGetAllInvestmentsWithRelations() {
        List<Investment> expected = List.of(new Investment());

        when(investmentRepository.findAllWithRelations())
                .thenReturn(expected);

        assertEquals(
                expected,
                investmentService.getAllInvestments()
        );
    }
}
