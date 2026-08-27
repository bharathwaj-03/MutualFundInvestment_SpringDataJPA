package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Redemption;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.redemption.I_RedemptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RedemptionControllerTest {

    @Mock
    private I_RedemptionService redemptionService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new RedemptionController(
                                redemptionService
                        )
                )
                .build();
    }

    @Test
    void shouldCalculateRedemptionUsingSessionInvestor()
            throws Exception {

        Investor investor =
                new Investor();

        investor.setUserId("INV001");
        investor.setName("Bharath");

        Redemption redemption =
                new Redemption();

        redemption.setInvestor(investor);
        redemption.setUnitsRedeemed(1);
        redemption.setNavAtRedemption(590);
        redemption.setGrossAmount(590);
        redemption.setBrokerageCharges(5);
        redemption.setAmountReceived(585);

        when(redemptionService
                .calculateRedemption(
                        "INV001",
                        "HLD001",
                        1
                ))
                .thenReturn(redemption);

        MockHttpSession session =
                new MockHttpSession();

        session.setAttribute(
                "USER_ID",
                "INV001"
        );

        mockMvc.perform(
                        post(
                                "/api/redemptions/calculate"
                        )
                                .session(session)
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "holdingId":"HLD001",
                                          "units":1
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.investorId")
                        .value("INV001"))
                .andExpect(jsonPath("$.grossAmount")
                        .value(590))
                .andExpect(jsonPath("$.amountReceived")
                        .value(585));
    }

    @Test
    void shouldGetRedemptionById()
            throws Exception {

        Redemption redemption =
                new Redemption();

        redemption.setRedemptionId(
                "RED001"
        );

        when(redemptionService
                .getRedemptionById("RED001"))
                .thenReturn(redemption);

        mockMvc.perform(
                        get(
                                "/api/redemptions/RED001"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.redemptionId")
                        .value("RED001"));
    }

    @Test
    void shouldGetInvestorRedemptions()
            throws Exception {

        Redemption redemption =
                new Redemption();

        redemption.setRedemptionId(
                "RED001"
        );

        when(redemptionService
                .getRedemptionsByUser("INV001"))
                .thenReturn(
                        List.of(redemption)
                );

        mockMvc.perform(
                        get(
                                "/api/redemptions/investor/INV001"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].redemptionId")
                        .value("RED001"));
    }
}
