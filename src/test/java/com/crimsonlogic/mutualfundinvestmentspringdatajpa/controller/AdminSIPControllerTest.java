package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.SIP;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.sip.I_SIPService;
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

class AdminSIPControllerTest {

    @Mock
    private I_SIPService sipService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new AdminSIPController(
                                sipService
                        )
                )
                .build();
    }

    @Test
    void shouldGetAllSips()
            throws Exception {

        SIP sip = new SIP();
        sip.setSipId("SIP001");
        sip.setMonthlyAmount(5000);
        sip.setSipStatus("ACTIVE");

        when(sipService.getAllSIPs())
                .thenReturn(List.of(sip));

        mockMvc.perform(
                        get("/api/admin/sip")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sipId")
                        .value("SIP001"))
                .andExpect(jsonPath("$[0].monthlyAmount")
                        .value(5000))
                .andExpect(jsonPath("$[0].sipStatus")
                        .value("ACTIVE"));
    }

    @Test
    void shouldGetSipById()
            throws Exception {

        SIP sip = new SIP();
        sip.setSipId("SIP001");

        when(sipService.getSIPById("SIP001"))
                .thenReturn(sip);

        mockMvc.perform(
                        get("/api/admin/sip/SIP001")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sipId")
                        .value("SIP001"));
    }
}
