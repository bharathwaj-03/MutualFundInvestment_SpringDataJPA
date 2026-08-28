package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.InvestorProfileUpdateRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.response.InvestorProfileResponse;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.response.NomineeProfileResponse;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.GlobalExceptionHandler;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.ResourceNotFoundException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investor.I_InvestorService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet
        .request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet
        .result.MockMvcResultMatchers.*;


class InvestorProfileControllerTest {

    @Mock
    private I_InvestorService investorService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        LocalValidatorFactoryBean validator =
                new LocalValidatorFactoryBean();

        validator.afterPropertiesSet();


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
                        .setValidator(
                                validator
                        )
                        .build();
    }


    private InvestorProfileResponse
    profile() {

        NomineeProfileResponse nominee =
                new NomineeProfileResponse(
                        "NOM001",
                        "RAHUL",
                        30,
                        "MALE",
                        "BROTHER",
                        "123456789012"
                );


        return new InvestorProfileResponse(
                "INV001",
                "BHARATH KUMAR",
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
    }


    @Test
    void shouldGetInvestorProfile()
            throws Exception {

        when(
                investorService
                        .getInvestorProfile(
                                "INV001"
                        )
        )
                .thenReturn(
                        profile()
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
                                .value(
                                        "BHARATH KUMAR"
                                )
                )
                .andExpect(
                        jsonPath("$.password")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.panNumber")
                                .value(
                                        "ABCDE1234F"
                                )
                )
                .andExpect(
                        jsonPath("$.nominee.name")
                                .value(
                                        "RAHUL"
                                )
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
    void shouldUpdateInvestorProfile()
            throws Exception {

        when(
                investorService
                        .updateInvestorProfile(
                                eq("INV001"),
                                any(
                                        InvestorProfileUpdateRequest.class
                                )
                        )
        )
                .thenReturn(
                        profile()
                );


        mockMvc.perform(
                        put(
                                "/api/investors/INV001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Bharath Kumar",
                                          "email":"bharath@example.com",
                                          "phoneNumber":"9876543210",
                                          "panNumber":"ABCDE1234F",
                                          "accountNumber":"123456789012",
                                          "riskProfile":"MODERATE",
                                          "nominee":{
                                            "name":"Rahul Kumar",
                                            "age":30,
                                            "gender":"MALE",
                                            "relationship":"BROTHER",
                                            "accountNumber":"123456789012"
                                          }
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value("INV001")
                );


        verify(
                investorService
        )
                .updateInvestorProfile(
                        eq("INV001"),
                        any(
                                InvestorProfileUpdateRequest.class
                        )
                );
    }


    @Test
    void shouldReturn400ForInvalidInvestorUpdate()
            throws Exception {

        mockMvc.perform(
                        put(
                                "/api/investors/INV001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"AAA",
                                          "email":"invalid",
                                          "phoneNumber":"123",
                                          "panNumber":"ABC",
                                          "accountNumber":"12",
                                          "riskProfile":"",
                                          "nominee":{
                                            "name":"BBB",
                                            "age":0,
                                            "gender":"OTHER",
                                            "relationship":"",
                                            "accountNumber":"12"
                                          }
                                        }
                                        """)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(400)
                )
                .andExpect(
                        jsonPath("$.fieldErrors")
                                .exists()
                );


        verify(
                investorService,
                never()
        )
                .updateInvestorProfile(
                        anyString(),
                        any(
                                InvestorProfileUpdateRequest.class
                        )
                );
    }

    @AfterEach
    void tearDown() {

        reset(
                investorService
        );
    }

}