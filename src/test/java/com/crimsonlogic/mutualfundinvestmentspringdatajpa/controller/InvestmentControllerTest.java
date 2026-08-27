package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment.I_InvestmentService;
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

class InvestmentControllerTest {

    @Mock
    private I_InvestmentService investmentService;

    private MockMvc mockMvc;

    private MockHttpSession investorSession;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new InvestmentController(
                                investmentService
                        )
                )
                .build();

        investorSession =
                new MockHttpSession();

        investorSession.setAttribute(
                "USER_ID",
                "INV001"
        );

        investorSession.setAttribute(
                "ROLE",
                "INVESTOR"
        );
    }

    private Investment investment(
            String investmentId) {

        Investor investor =
                new Investor();

        investor.setUserId("INV001");
        investor.setName("Bharath");

        Investment investment =
                new Investment();

        investment.setInvestmentId(
                investmentId
        );

        investment.setInvestor(
                investor
        );

        investment.setAmount(
                10000
        );

        investment.setUnitsPurchased(
                20
        );

        return investment;
    }

    @Test
    void shouldGetLoggedInInvestorInvestments()
            throws Exception {

        when(investmentService
                .getInvestmentsByUser("INV001"))
                .thenReturn(
                        List.of(
                                investment("INVT001")
                        )
                );

        mockMvc.perform(
                        get(
                                "/api/investor/investments"
                        )
                                .session(
                                        investorSession
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].investmentId")
                        .value("INVT001"))
                .andExpect(jsonPath("$[0].investorId")
                        .value("INV001"))
                .andExpect(jsonPath("$[0].amount")
                        .value(10000));
    }

    @Test
    void shouldGetOwnedInvestmentById()
            throws Exception {

        when(investmentService
                .getInvestmentById("INVT001"))
                .thenReturn(
                        investment("INVT001")
                );

        mockMvc.perform(
                        get(
                                "/api/investor/investments/INVT001"
                        )
                                .session(
                                        investorSession
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.investmentId")
                        .value("INVT001"))
                .andExpect(jsonPath("$.investorId")
                        .value("INV001"));
    }
}
