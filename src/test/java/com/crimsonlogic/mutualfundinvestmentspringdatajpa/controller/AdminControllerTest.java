package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.FundCategoryPerformanceResponse;
import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.abstraction.MutualFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.fund.EquityFund;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.holding.I_HoldingService;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund.I_MutualFundService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet
        .request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet
        .result.MockMvcResultMatchers.*;


class AdminControllerTest {


    @Mock
    private I_MutualFundService mutualFundService;


    @Mock
    private I_HoldingService holdingService;

    @Mock
    private I_InvestorService investorService;


    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc =
                MockMvcBuilders
                        .standaloneSetup(
                                new AdminController(
                                        mutualFundService,
                                        holdingService,
                                        investorService
                                )
                        )
                        .build();
    }





    @Test
    void shouldReturnFundsForAdmin()
            throws Exception {

        MutualFund fund =
                new EquityFund();

        fund.setFundId(
                "FND001"
        );


        when(
                mutualFundService.getAllFunds()
        ).thenReturn(
                List.of(fund)
        );


        mockMvc.perform(
                        get(
                                "/api/admin/funds"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$[0].fundId")
                                .value(
                                        "FND001"
                                )
                );
    }
    @Test
    void shouldReturnFundCategoryPerformance()
            throws Exception {

        FundCategoryPerformanceResponse response =
                new FundCategoryPerformanceResponse(
                        "Equity Fund",
                        100000,
                        125000
                );


        when(
                holdingService
                        .getFundCategoryPerformance()
        ).thenReturn(
                List.of(response)
        );


        mockMvc.perform(
                        get(
                                "/api/admin/fund-category-performance"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath(
                                "$[0].fundCategory"
                        )
                                .value(
                                        "Equity Fund"
                                )
                )
                .andExpect(
                        jsonPath(
                                "$[0].currentValue"
                        )
                                .value(
                                        125000
                                )
                )
                .andExpect(
                        jsonPath(
                                "$[0].profitOrLoss"
                        )
                                .value(
                                        25000
                                )
                )
                .andExpect(
                        jsonPath(
                                "$[0].returnPercentage"
                        )
                                .value(
                                        25
                                )
                );
    }
}