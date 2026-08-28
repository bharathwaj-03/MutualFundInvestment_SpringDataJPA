package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.mutualfund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.InvalidRequestException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.ResourceNotFoundException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.abstraction.MutualFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.fund.EquityFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.nav.NAVHistory;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.MutualFundRepository;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.NAVHistoryRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;


class MutualFundServiceTest {


    @Mock
    private MutualFundRepository
            mutualFundRepository;


    @Mock
    private NAVHistoryRepository
            navHistoryRepository;


    private AutoCloseable mocks;

    private MutualFundService
            mutualFundService;


    @BeforeEach
    void setUp() {

        mocks = MockitoAnnotations
                .openMocks(this);


        mutualFundService =
                new MutualFundService(
                        mutualFundRepository,
                        navHistoryRepository
                );
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }


    /**
     * Creates a valid mutual fund object used by service tests.
     */
    private MutualFund validFund() {

        MutualFund fund =
                new EquityFund();


        fund.setFundId(
                "FND001"
        );


        fund.setFundCode(
                "EQ001"
        );


        fund.setFundName(
                "Test Equity Fund"
        );


        fund.setFundCategory(
                "Equity Fund"
        );


        fund.setFundHouse(
                "ABC Mutual Fund"
        );


        fund.setRiskLevel(
                "HIGH"
        );


        fund.setNav(
                500
        );


        fund.setMinimumInvestment(
                500
        );


        fund.setSipGainPerYear(
                10
        );


        fund.setLumpSumGainPerYear(
                12
        );


        return fund;
    }


    // =========================================================
    // ADD FUND
    // =========================================================

    @Test
    void shouldAddValidFund() {

        MutualFund fund =
                validFund();


        mutualFundService
                .addFund(
                        fund
                );


        verify(
                mutualFundRepository,
                times(1)
        )
                .save(
                        fund
                );
    }


    @Test
    void shouldRejectNullFund() {

        assertThrows(
                InvalidRequestException.class,
                () ->
                        mutualFundService
                                .addFund(
                                        null
                                )
        );


        verify(
                mutualFundRepository,
                never()
        )
                .save(
                        any(MutualFund.class)
                );
    }


    @Test
    void shouldGenerateFundIdWhenFundIdIsMissing() {

        MutualFund fund =
                validFund();


        fund.setFundId(
                null
        );


        mutualFundService
                .addFund(
                        fund
                );


        assertNotNull(
                fund.getFundId()
        );


        verify(
                mutualFundRepository
        )
                .save(
                        fund
                );
    }


    // =========================================================
    // UPDATE FUND
    // =========================================================

    @Test
    void shouldUpdateExistingFund() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        fund.setFundName(
                "Updated Equity Fund"
        );


        mutualFundService
                .updateFund(
                        fund
                );


        verify(
                mutualFundRepository
        )
                .findById(
                        "FND001"
                );


        verify(
                mutualFundRepository
        )
                .save(
                        fund
                );
    }


    @Test
    void shouldThrowWhenUpdatingMissingFund() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .updateFund(
                                        fund
                                )
        );


        verify(
                mutualFundRepository,
                never()
        )
                .save(
                        any(MutualFund.class)
                );
    }


    @Test
    void shouldRejectNullFundDuringUpdate() {

        assertThrows(
                InvalidRequestException.class,
                () ->
                        mutualFundService
                                .updateFund(
                                        null
                                )
        );
    }


    // =========================================================
    // GET FUND BY ID
    // =========================================================

    @Test
    void shouldReturnFundById() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        MutualFund result =
                mutualFundService
                        .getFundById(
                                "FND001"
                        );


        assertSame(
                fund,
                result
        );


        verify(
                mutualFundRepository
        )
                .findById(
                        "FND001"
                );
    }


    @Test
    void shouldThrowWhenFundNotFound() {

        when(
                mutualFundRepository
                        .findById(
                                "FND404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .getFundById(
                                        "FND404"
                                )
        );
    }


    @Test
    void shouldRejectBlankFundId() {

        assertThrows(
                InvalidRequestException.class,
                () ->
                        mutualFundService
                                .getFundById(
                                        ""
                                )
        );


        verify(
                mutualFundRepository,
                never()
        )
                .findById(
                        anyString()
                );
    }


    // =========================================================
    // GET FUND BY NAME
    // =========================================================

    @Test
    void shouldReturnFundByName() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findByFundName(
                                "Test Equity Fund"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        MutualFund result =
                mutualFundService
                        .getFundByName(
                                "Test Equity Fund"
                        );


        assertSame(
                fund,
                result
        );
    }


    @Test
    void shouldThrowWhenFundNameDoesNotExist() {

        when(
                mutualFundRepository
                        .findByFundName(
                                "Unknown Fund"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .getFundByName(
                                        "Unknown Fund"
                                )
        );
    }


    // =========================================================
    // GET FUNDS BY CATEGORY
    // =========================================================

    @Test
    void shouldGetFundsByCategory() {

        List<MutualFund> expected =
                List.of(
                        validFund()
                );


        when(
                mutualFundRepository
                        .findByFundCategoryOrderByFundNameAsc(
                                "Equity Fund"
                        )
        )
                .thenReturn(
                        expected
                );


        List<MutualFund> result =
                mutualFundService
                        .getFundsByCategory(
                                "Equity Fund"
                        );


        assertEquals(
                expected,
                result
        );


        verify(
                mutualFundRepository
        )
                .findByFundCategoryOrderByFundNameAsc(
                        "Equity Fund"
                );
    }


    @Test
    void shouldRejectBlankFundCategory() {

        assertThrows(
                InvalidRequestException.class,
                () ->
                        mutualFundService
                                .getFundsByCategory(
                                        ""
                                )
        );
    }


    // =========================================================
    // GET ALL FUNDS
    // =========================================================

    @Test
    void shouldReturnAllFunds() {

        List<MutualFund> expected =
                List.of(
                        validFund()
                );


        when(
                mutualFundRepository
                        .findAllByOrderByFundCategoryAscFundNameAsc()
        )
                .thenReturn(
                        expected
                );


        List<MutualFund> result =
                mutualFundService
                        .getAllFunds();


        assertEquals(
                expected,
                result
        );


        verify(
                mutualFundRepository
        )
                .findAllByOrderByFundCategoryAscFundNameAsc();
    }


    // =========================================================
    // DELETE FUND
    // =========================================================

    @Test
    void shouldDeleteExistingFund() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        mutualFundService
                .deleteFund(
                        "FND001"
                );


        verify(
                mutualFundRepository
        )
                .deleteById(
                        "FND001"
                );
    }


    @Test
    void shouldThrowWhenDeletingMissingFund() {

        when(
                mutualFundRepository
                        .findById(
                                "FND404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .deleteFund(
                                        "FND404"
                                )
        );


        verify(
                mutualFundRepository,
                never()
        )
                .deleteById(
                        anyString()
                );
    }


    // =========================================================
    // UPDATE NAV
    // =========================================================

    @Test
    void shouldUpdateNavAndCreateNavHistory() {

        MutualFund fund =
                validFund();


        double oldNav =
                fund.getNav();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        mutualFundService
                .updateNAV(
                        "FND001",
                        600.0,
                        "ADM001"
                );


        assertEquals(
                600.0,
                fund.getNav()
        );


        verify(
                mutualFundRepository
        )
                .save(
                        fund
                );


        verify(
                navHistoryRepository
        )
                .save(
                        argThat(
                                history ->

                                        history != null

                                                && history
                                                .getMutualFund()
                                                == fund

                                                && Double.compare(
                                                history.getOldNav(),
                                                oldNav
                                        ) == 0

                                                && Double.compare(
                                                history.getNewNav(),
                                                600.0
                                        ) == 0

                                                && "ADM001"
                                                .equals(
                                                        history.getChangedBy()
                                                )
                        )
                );
    }


    @Test
    void shouldRejectSameNavAsCurrentNav() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        InvalidRequestException exception =
                assertThrows(
                        InvalidRequestException.class,
                        () ->
                                mutualFundService
                                        .updateNAV(
                                                "FND001",
                                                500.0,
                                                "ADM001"
                                        )
                );


        assertEquals(
                "New NAV is same as current NAV.",
                exception.getMessage()
        );


        verify(
                mutualFundRepository,
                never()
        )
                .save(
                        any(MutualFund.class)
                );


        verify(
                navHistoryRepository,
                never()
        )
                .save(
                        any(NAVHistory.class)
                );
    }


    @Test
    void shouldThrowWhenUpdatingNavForMissingFund() {

        when(
                mutualFundRepository
                        .findById(
                                "FND404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .updateNAV(
                                        "FND404",
                                        600.0,
                                        "ADM001"
                                )
        );


        verify(
                navHistoryRepository,
                never()
        )
                .save(
                        any(NAVHistory.class)
                );
    }


    // =========================================================
    // CURRENT NAV
    // =========================================================

    @Test
    void shouldReturnCurrentNav() {

        MutualFund fund =
                validFund();


        when(
                mutualFundRepository
                        .findById(
                                "FND001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                fund
                        )
                );


        double nav =
                mutualFundService
                        .getCurrentNav(
                                "FND001"
                        );


        assertEquals(
                500.0,
                nav
        );
    }


    @Test
    void shouldThrowWhenGettingCurrentNavForMissingFund() {

        when(
                mutualFundRepository
                        .findById(
                                "FND404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        mutualFundService
                                .getCurrentNav(
                                        "FND404"
                                )
        );
    }
}