package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Holding;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.holding.I_HoldingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HoldingControllerTest {

    @Mock
    private I_HoldingService holdingService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new HoldingController(
                                holdingService
                        )
                )
                .build();
    }

    @Test
    void shouldReturnLoggedInInvestorHoldings()
            throws Exception {

        MockHttpSession session =
                new MockHttpSession();

        session.setAttribute(
                "USER_ID",
                "INV001"
        );

        Portfolio portfolio =
                new Portfolio();

        portfolio.setPortfolioId(
                "PORT001"
        );

        EquityFund fund =
                new EquityFund();

        fund.setFundId("FND001");
        fund.setFundName("Demo Equity");
        fund.setFundCategory("Equity Fund");
        fund.setNav(600);

        Holding holding =
                new Holding();

        holding.setHoldingId("HLD001");
        holding.setPortfolio(portfolio);
        holding.setMutualFund(fund);
        holding.setUnitsOwned(10);
        holding.setInvestedAmount(5000);
        holding.setAverageNav(500);

        when(holdingService
                .getHoldingsByInvestor("INV001"))
                .thenReturn(
                        List.of(holding)
                );

        mockMvc.perform(
                        get(
                                "/api/investor/holdings"
                        )
                                .session(session)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].holdingId")
                        .value("HLD001"))
                .andExpect(jsonPath("$[0].fundId")
                        .value("FND001"))
                .andExpect(jsonPath("$[0].currentValue")
                        .value(6000))
                .andExpect(jsonPath("$[0].profitOrLoss")
                        .value(1000));
    }
}
