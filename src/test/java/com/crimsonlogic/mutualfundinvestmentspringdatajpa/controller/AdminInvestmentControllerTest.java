package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investment.I_InvestmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminInvestmentControllerTest {

    @Mock
    private I_InvestmentService investmentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new AdminInvestmentController(
                                investmentService
                        )
                )
                .build();
    }

    @Test
    void shouldGetAllInvestments()
            throws Exception {

        Investment investment =
                new Investment();

        investment.setInvestmentId(
                "INVT001"
        );

        investment.setAmount(
                5000
        );

        when(investmentService
                .getAllInvestments())
                .thenReturn(
                        List.of(investment)
                );

        mockMvc.perform(
                        get(
                                "/api/admin/investments"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].investmentId")
                        .value("INVT001"))
                .andExpect(jsonPath("$[0].amount")
                        .value(5000));
    }

    @Test
    void shouldGetInvestmentById()
            throws Exception {

        Investment investment =
                new Investment();

        investment.setInvestmentId(
                "INVT001"
        );

        when(investmentService
                .getInvestmentById("INVT001"))
                .thenReturn(investment);

        mockMvc.perform(
                        get(
                                "/api/admin/investments/INVT001"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.investmentId")
                        .value("INVT001"));
    }

    @AfterEach
    void tearDown() {

        reset(
                investmentService
        );
    }

}
