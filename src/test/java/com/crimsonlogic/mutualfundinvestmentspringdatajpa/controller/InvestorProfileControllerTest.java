package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.NomineeProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.GlobalExceptionHandler;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InvestorProfileControllerTest {

    @Mock
    private I_InvestorService investorService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc =
                MockMvcBuilders
                        .standaloneSetup(
                                new InvestorProfileController(
                                        investorService
                                )
                        )
                        .setControllerAdvice(
                                new GlobalExceptionHandler()
                        )
                        .build();
    }


    @Test
    void shouldGetInvestorProfile()
            throws Exception {

        NomineeProfileResponse nominee =
                new NomineeProfileResponse(
                        "NOM001",
                        "RAHUL",
                        30,
                        "MALE",
                        "BROTHER",
                        "123456789012"
                );


        InvestorProfileResponse profile =
                new InvestorProfileResponse(
                        "INV001",
                        "Bharath",
                        "bharath@example.com",
                        "9876543210",
                        "INVESTOR",
                        25,
                        "ABCDE1234F",
                        "123456789012",
                        LocalDate.of(
                                2026,
                                8,
                                25
                        ),
                        "MODERATE",
                        true,
                        nominee
                );


        when(
                investorService
                        .getInvestorProfile(
                                "INV001"
                        )
        )
                .thenReturn(
                        profile
                );


        mockMvc.perform(
                        get(
                                "/api/investors/INV001"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value("INV001")
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Bharath")
                )
                .andExpect(
                        jsonPath("$.password")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.panNumber")
                                .value("ABCDE1234F")
                )
                .andExpect(
                        jsonPath("$.nominee.name")
                                .value("RAHUL")
                );
    }


    @Test
    void shouldReturn404ForMissingInvestor()
            throws Exception {

        when(
                investorService
                        .getInvestorProfile(
                                "INV404"
                        )
        )
                .thenThrow(
                        new ResourceNotFoundException(
                                "Investor not found with id: INV404"
                        )
                );


        mockMvc.perform(
                        get(
                                "/api/investors/INV404"
                        )
                )
                .andExpect(
                        status().isNotFound()
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                );
    }


    @Test
    void shouldReturn400WhenInvestorUpdateFails()
            throws Exception {

        when(
                investorService
                        .updateInvestorProfile(
                                any(Investor.class)
                        )
        )
                .thenReturn(false);


        mockMvc.perform(
                        put(
                                "/api/investors/INV001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Bharath"
                                        }
                                        """)
                )
                .andExpect(
                        status().isBadRequest()
                );
    }
}