package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.GlobalExceptionHandler;

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
        .services.mutualfund.I_MutualFundService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.navhistory.I_NAVHistoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class MutualFundControllerTest {

    @Mock
    private I_MutualFundService
            mutualFundService;

    @Mock
    private I_NAVHistoryService
            navHistoryService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        MockitoAnnotations
                .openMocks(this);

        mockMvc =
                MockMvcBuilders
                        .standaloneSetup(
                                new MutualFundController(
                                        mutualFundService,
                                        navHistoryService
                                )
                        )
                        .setControllerAdvice(
                                new GlobalExceptionHandler()
                        )
                        .build();
    }


    // =========================================================
    // ADD FUND
    // =========================================================

    @Test
    void shouldAddFund()
            throws Exception {

        mockMvc.perform(
                        post(
                                "/api/admin/funds"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "fundCode":"EQ001",
                                          "fundName":"Equity Growth Fund",
                                          "fundHouse":"ABC Mutual Fund",
                                          "fundCategory":"Equity Fund",
                                          "riskLevel":"HIGH",
                                          "nav":100.0,
                                          "minimumInvestment":5000.0,
                                          "sipGainPerYear":12.0,
                                          "lumpSumGainPerYear":15.0
                                        }
                                        """)
                )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        jsonPath("$.fundCode")
                                .value("EQ001")
                )
                .andExpect(
                        jsonPath("$.fundName")
                                .value(
                                        "Equity Growth Fund"
                                )
                );

        verify(
                mutualFundService,
                times(1)
        )
                .addFund(
                        any(MutualFund.class)
                );
    }


    // =========================================================
    // UPDATE FUND
    // =========================================================

    @Test
    void shouldUpdateFund()
            throws Exception {

        MutualFund existing =
                new EquityFund();

        existing.setFundId(
                "FND001"
        );

        existing.setFundName(
                "Old Fund"
        );

        when(
                mutualFundService
                        .getFundById(
                                "FND001"
                        )
        )
                .thenReturn(
                        existing
                );

        mockMvc.perform(
                        put(
                                "/api/admin/funds/FND001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "fundCode":"EQ001",
                                          "fundName":"Updated Equity Fund",
                                          "fundHouse":"ABC Mutual Fund",
                                          "fundCategory":"Equity Fund",
                                          "riskLevel":"HIGH",
                                          "nav":120.0,
                                          "minimumInvestment":5000.0,
                                          "sipGainPerYear":12.0,
                                          "lumpSumGainPerYear":15.0
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.fundId")
                                .value("FND001")
                )
                .andExpect(
                        jsonPath("$.fundName")
                                .value(
                                        "Updated Equity Fund"
                                )
                );

        verify(
                mutualFundService
        )
                .updateFund(
                        existing
                );
    }


    // =========================================================
    // UPDATE MISSING FUND
    // =========================================================

    @Test
    void shouldReturn404WhenUpdatingMissingFund()
            throws Exception {

        when(
                mutualFundService
                        .getFundById(
                                "FND404"
                        )
        )
                .thenThrow(
                        new ResourceNotFoundException(
                                "Mutual fund not found with id: FND404"
                        )
                );

        mockMvc.perform(
                        put(
                                "/api/admin/funds/FND404"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "fundCode":"EQ001",
                                          "fundName":"Equity Fund",
                                          "fundHouse":"ABC",
                                          "fundCategory":"Equity Fund",
                                          "riskLevel":"HIGH",
                                          "nav":100.0,
                                          "minimumInvestment":5000.0,
                                          "sipGainPerYear":10.0,
                                          "lumpSumGainPerYear":12.0
                                        }
                                        """)
                )
                .andExpect(
                        status().isNotFound()
                );

        verify(
                mutualFundService,
                never()
        )
                .updateFund(
                        any(MutualFund.class)
                );
    }


    // =========================================================
    // DELETE FUND
    // =========================================================

    @Test
    void shouldDeleteFund()
            throws Exception {

        MutualFund fund =
                new EquityFund();

        fund.setFundId(
                "FND001"
        );

        when(
                mutualFundService
                        .getFundById(
                                "FND001"
                        )
        )
                .thenReturn(
                        fund
                );

        mockMvc.perform(
                        delete(
                                "/api/admin/funds/FND001"
                        )
                )
                .andExpect(
                        status().isNoContent()
                );

        verify(
                mutualFundService
        )
                .deleteFund(
                        "FND001"
                );
    }


    // =========================================================
    // DELETE MISSING FUND
    // =========================================================

    @Test
    void shouldReturn404WhenDeletingMissingFund()
            throws Exception {

        doThrow(
                new ResourceNotFoundException(
                        "Mutual fund not found with id: FND404"
                )
        )
                .when(
                        mutualFundService
                )
                .deleteFund(
                        "FND404"
                );

        mockMvc.perform(
                        delete(
                                "/api/admin/funds/FND404"
                        )
                )
                .andExpect(
                        status().isNotFound()
                );

        verify(
                mutualFundService
        )
                .deleteFund(
                        "FND404"
                );
    }


    // =========================================================
    // UPDATE NAV
    // =========================================================

    @Test
    void shouldUpdateNAV()
            throws Exception {

        MutualFund fund =
                new EquityFund();

        fund.setFundId(
                "FND001"
        );

        fund.setNav(
                120.0
        );

        when(
                mutualFundService
                        .getFundById(
                                "FND001"
                        )
        )
                .thenReturn(
                        fund
                );

        mockMvc.perform(
                        patch(
                                "/api/admin/funds/FND001/nav"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "newNav":120.0,
                                          "adminId":"ADM001"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.fundId")
                                .value("FND001")
                )
                .andExpect(
                        jsonPath("$.nav")
                                .value(120.0)
                );

        verify(
                mutualFundService
        )
                .updateNAV(
                        "FND001",
                        120.0,
                        "ADM001"
                );
    }


    // =========================================================
    // UPDATE NAV - FUND NOT FOUND
    // =========================================================

    @Test
    void shouldReturn404WhenUpdatingNAVForMissingFund()
            throws Exception {

        doThrow(
                new ResourceNotFoundException(
                        "Mutual fund not found with id: FND404"
                )
        )
                .when(
                        mutualFundService
                )
                .updateNAV(
                        "FND404",
                        120.0,
                        "ADM001"
                );

        mockMvc.perform(
                        patch(
                                "/api/admin/funds/FND404/nav"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "newNav":120.0,
                                          "adminId":"ADM001"
                                        }
                                        """)
                )
                .andExpect(
                        status().isNotFound()
                );

        verify(
                mutualFundService
        )
                .updateNAV(
                        "FND404",
                        120.0,
                        "ADM001"
                );
    }


    // =========================================================
    // GET ALL NAV HISTORY
    // =========================================================

    @Test
    void shouldGetAllNavHistory()
            throws Exception {

        NAVHistory history =
                new NAVHistory();

        history.setHistoryId(
                "NAV001"
        );

        when(
                navHistoryService
                        .getAllNAVHistory()
        )
                .thenReturn(
                        List.of(
                                history
                        )
                );

        mockMvc.perform(
                        get(
                                "/api/admin/funds/nav-history"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath(
                                "$[0].historyId"
                        )
                                .value(
                                        "NAV001"
                                )
                );

        verify(
                navHistoryService
        )
                .getAllNAVHistory();
    }


    // =========================================================
    // GET NAV HISTORY BY FUND
    // =========================================================

    @Test
    void shouldGetNavHistoryForFund()
            throws Exception {

        NAVHistory history =
                new NAVHistory();

        history.setHistoryId(
                "NAV001"
        );

        MutualFund fund =
                new EquityFund();

        fund.setFundId(
                "FND001"
        );

        fund.setFundName(
                "Equity Growth Fund"
        );

        history.setMutualFund(
                fund
        );

        when(
                navHistoryService
                        .getNAVHistoryByFundId(
                                "FND001"
                        )
        )
                .thenReturn(
                        List.of(
                                history
                        )
                );

        mockMvc.perform(
                        get(
                                "/api/admin/funds/FND001/nav-history"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath(
                                "$[0].historyId"
                        )
                                .value(
                                        "NAV001"
                                )
                )
                .andExpect(
                        jsonPath(
                                "$[0].fundId"
                        )
                                .value(
                                        "FND001"
                                )
                );

        verify(
                navHistoryService
        )
                .getNAVHistoryByFundId(
                        "FND001"
                );
    }
}