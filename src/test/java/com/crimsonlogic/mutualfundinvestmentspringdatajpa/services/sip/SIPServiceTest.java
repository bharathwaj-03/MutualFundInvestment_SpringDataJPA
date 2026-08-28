package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.sip;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.SIPRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund.I_MutualFundService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment.I_PaymentService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.transaction.I_TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SIPServiceTest {

    @Mock
    private SIPRepository sipRepository;
    @Mock
    private AutoCloseable mocks;

    private I_InvestorService investorService;
    @Mock
    private I_MutualFundService mutualFundService;
    @Mock
    private I_HoldingService holdingService;
    @Mock
    private I_PortfolioService portfolioService;
    @Mock
    private I_TransactionService transactionService;
    @Mock
    private I_PaymentService paymentService;

    private SIPService sipService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        sipService = new SIPService(
                sipRepository,
                investorService,
                mutualFundService,
                holdingService,
                portfolioService,
                transactionService,
                paymentService
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void shouldReturnValidationErrorForMissingFund() {
        Map<String, String> errors =
                sipService.validateSIP(
                        null,
                        5000,
                        5,
                        LocalDate.now().plusDays(2),
                        "UPI"
                );

        assertTrue(errors.containsKey("fundId"));
    }

    @Test
    void shouldReturnValidationErrorForInvalidMonthlyAmount() {
        Map<String, String> errors =
                sipService.validateSIP(
                        "FND001",
                        0,
                        5,
                        LocalDate.now().plusDays(2),
                        "UPI"
                );

        assertTrue(errors.containsKey("monthlyAmount"));
    }

    @Test
    void shouldGetSipByIdWithRelations() {
        SIP sip = new SIP();

        when(sipRepository.findByIdWithRelations("SIP001"))
                .thenReturn(Optional.of(sip));

        assertSame(sip, sipService.getSIPById("SIP001"));
    }

    @Test
    void shouldThrowWhenSipDoesNotExist() {
        when(sipRepository.findByIdWithRelations("SIP404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sipService.getSIPById("SIP404")
        );
    }

    @Test
    void shouldGetInvestorSipsWithRelations() {
        List<SIP> expected = List.of(new SIP());

        when(sipRepository.findByInvestorIdWithRelations("INV001"))
                .thenReturn(expected);

        assertEquals(
                expected,
                sipService.getSIPsByUser("INV001")
        );
    }

    @Test
    void shouldGetAllSipsWithRelations() {
        List<SIP> expected = List.of(new SIP());

        when(sipRepository.findAllWithRelations())
                .thenReturn(expected);

        assertEquals(expected, sipService.getAllSIPs());
    }

    @Test
    void shouldCancelActiveSip() {
        SIP sip = new SIP();
        sip.setSipId("SIP001");
        sip.setSipStatus("ACTIVE");

        when(sipRepository.findById("SIP001"))
                .thenReturn(Optional.of(sip));

        assertTrue(sipService.cancelSIP("SIP001"));
        assertEquals("CANCELLED", sip.getSipStatus());

        verify(sipRepository).save(sip);
    }
}
