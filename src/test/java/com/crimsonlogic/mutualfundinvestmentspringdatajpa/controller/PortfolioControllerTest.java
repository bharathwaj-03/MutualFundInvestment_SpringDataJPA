package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PortfolioControllerTest {

    @Mock
    private I_PortfolioService portfolioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new PortfolioController(
                                portfolioService
                        )
                )
                .build();
    }

    @Test
    void shouldReturnInvestorPortfolio()
            throws Exception {

        Investor investor =
                new Investor();

        investor.setUserId("INV001");
        investor.setName("Bharath");

        Portfolio portfolio =
                new Portfolio();

        portfolio.setPortfolioId(
                "PORT001"
        );

        portfolio.setInvestor(
                investor
        );

        portfolio.setLastActivityDate(
                LocalDate.of(
                        2026,
                        8,
                        23
                )
        );

        when(portfolioService
                .getPortfolio("INV001"))
                .thenReturn(portfolio);

        when(portfolioService
                .calculatePortfolioValue("INV001"))
                .thenReturn(42000.0);

        mockMvc.perform(
                        get(
                                "/api/portfolios/investor/INV001"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.portfolioId")
                        .value("PORT001"))
                .andExpect(jsonPath("$.investorId")
                        .value("INV001"))
                .andExpect(jsonPath("$.investorName")
                        .value("Bharath"))
                .andExpect(jsonPath("$.currentValue")
                        .value(42000.0));
    }

    @AfterEach
    void tearDown() {

        reset(
                portfolioService
        );
    }

}
