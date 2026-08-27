package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.InvestorRegistrationRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.GlobalExceptionHandler;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.user.Admin;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.user.Investor;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.admin.I_AdminService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investor.I_InvestorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpSession;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet
        .setup.MockMvcBuilders;

import org.springframework.validation
        .beanvalidation.LocalValidatorFactoryBean;


import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet
        .request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet
        .result.MockMvcResultMatchers.*;


class UserLoginControllerTest {


    @Mock
    private I_AdminService adminService;


    @Mock
    private I_InvestorService investorService;


    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        MockitoAnnotations
                .openMocks(this);


        /*
         * Standalone MockMvc does not automatically use the
         * application's MVC validator configuration.
         *
         * LocalValidatorFactoryBean enables @Valid validation
         * for controller request DTOs during these tests.
         */
        LocalValidatorFactoryBean validator =
                new LocalValidatorFactoryBean();

        validator.afterPropertiesSet();


        mockMvc =
                MockMvcBuilders
                        .standaloneSetup(
                                new UserLoginController(
                                        adminService,
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


    // =========================================================
    // ADMIN LOGIN SUCCESS
    // =========================================================

    @Test
    void shouldLoginAdminAndCreateSession()
            throws Exception {

        Admin admin =
                new Admin();

        admin.setUserId(
                "ADM001"
        );


        when(
                adminService
                        .authenticateAdmin(
                                "ADM001",
                                "Deep@37"
                        )
        )
                .thenReturn(
                        true
                );


        when(
                adminService
                        .getAdminByUserId(
                                "ADM001"
                        )
        )
                .thenReturn(
                        admin
                );


        mockMvc.perform(
                        post(
                                "/api/auth/admin/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"ADM001",
                                          "password":"Deep@37"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Admin login successful."
                                )
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value(
                                        "ADM001"
                                )
                )
                .andExpect(
                        jsonPath("$.role")
                                .value(
                                        "ADMIN"
                                )
                )
                .andExpect(
                        request()
                                .sessionAttribute(
                                        "USER_ID",
                                        "ADM001"
                                )
                )
                .andExpect(
                        request()
                                .sessionAttribute(
                                        "ROLE",
                                        "ADMIN"
                                )
                );
    }


    // =========================================================
    // ADMIN LOGIN FAILURE
    // =========================================================

    @Test
    void shouldReturn401ForInvalidAdminLogin()
            throws Exception {

        when(
                adminService
                        .authenticateAdmin(
                                "ADM001",
                                "Wrong"
                        )
        )
                .thenReturn(
                        false
                );


        mockMvc.perform(
                        post(
                                "/api/auth/admin/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"ADM001",
                                          "password":"Wrong"
                                        }
                                        """)
                )
                .andExpect(
                        status().isUnauthorized()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Invalid Admin ID or Password."
                                )
                );
    }


    // =========================================================
    // ADMIN LOGIN VALIDATION FAILURE
    // =========================================================

    @Test
    void shouldReturn400WhenAdminLoginFieldsAreBlank()
            throws Exception {

        mockMvc.perform(
                        post(
                                "/api/auth/admin/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"",
                                          "password":""
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
                        jsonPath("$.error")
                                .value(
                                        "Bad Request"
                                )
                )
                .andExpect(
                        jsonPath("$.fieldErrors.userId")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.password")
                                .exists()
                );


        /*
         * Bean Validation rejects the request before the
         * authentication service is called.
         */
        verify(
                adminService,
                never()
        )
                .authenticateAdmin(
                        anyString(),
                        anyString()
                );
    }


    // =========================================================
    // INVESTOR LOGIN SUCCESS
    // =========================================================

    @Test
    void shouldLoginInvestorAndCreateSession()
            throws Exception {

        Investor investor =
                new Investor();

        investor.setUserId(
                "INV001"
        );

        investor.setName(
                "Bharath"
        );


        when(
                investorService
                        .authenticateInvestor(
                                "INV001",
                                "Password@1"
                        )
        )
                .thenReturn(
                        investor
                );


        mockMvc.perform(
                        post(
                                "/api/auth/investor/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"INV001",
                                          "password":"Password@1"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Investor login successful."
                                )
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value(
                                        "INV001"
                                )
                )
                .andExpect(
                        jsonPath("$.name")
                                .value(
                                        "Bharath"
                                )
                )
                .andExpect(
                        jsonPath("$.role")
                                .value(
                                        "INVESTOR"
                                )
                )
                .andExpect(
                        request()
                                .sessionAttribute(
                                        "USER_ID",
                                        "INV001"
                                )
                )
                .andExpect(
                        request()
                                .sessionAttribute(
                                        "ROLE",
                                        "INVESTOR"
                                )
                );
    }


    // =========================================================
    // INVESTOR LOGIN FAILURE
    // =========================================================

    @Test
    void shouldReturn401ForInvalidInvestorLogin()
            throws Exception {

        when(
                investorService
                        .authenticateInvestor(
                                "INV001",
                                "Wrong"
                        )
        )
                .thenReturn(
                        null
                );


        mockMvc.perform(
                        post(
                                "/api/auth/investor/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"INV001",
                                          "password":"Wrong"
                                        }
                                        """)
                )
                .andExpect(
                        status().isUnauthorized()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Invalid Investor ID or Password."
                                )
                );
    }


    // =========================================================
    // INVESTOR LOGIN VALIDATION FAILURE
    // =========================================================

    @Test
    void shouldReturn400WhenInvestorLoginFieldsAreBlank()
            throws Exception {

        mockMvc.perform(
                        post(
                                "/api/auth/investor/login"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "userId":"",
                                          "password":""
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
                        jsonPath("$.fieldErrors.userId")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.password")
                                .exists()
                );


        verify(
                investorService,
                never()
        )
                .authenticateInvestor(
                        anyString(),
                        anyString()
                );
    }


    // =========================================================
    // INVESTOR REGISTRATION SUCCESS
    // =========================================================

    @Test
    void shouldRegisterInvestor()
            throws Exception {

        Investor registeredInvestor =
                new Investor();

        registeredInvestor.setUserId(
                "INV001"
        );

        registeredInvestor.setName(
                "BHARATH KUMAR"
        );


        when(
                investorService
                        .registerInvestor(
                                any(
                                        InvestorRegistrationRequest.class
                                )
                        )
        )
                .thenReturn(
                        registeredInvestor
                );


        mockMvc.perform(
                        post(
                                "/api/auth/investor/register"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Bharath Kumar",
                                          "email":"bharath@example.com",
                                          "phoneNumber":"9876543210",
                                          "password":"Password@1",
                                          "panNumber":"ABCDE1234F",
                                          "accountNumber":"123456789",
                                          "riskProfile":"MODERATE",
                                          "nominee":{
                                            "name":"Rahul Kumar",
                                            "age":30,
                                            "gender":"MALE",
                                            "relationship":"BROTHER",
                                            "accountNumber":"123456789"
                                          }
                                        }
                                        """)
                )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Investor registered successfully."
                                )
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value(
                                        "INV001"
                                )
                );


        verify(
                investorService,
                times(1)
        )
                .registerInvestor(
                        any(
                                InvestorRegistrationRequest.class
                        )
                );
    }


    // =========================================================
    // INVESTOR REGISTRATION VALIDATION FAILURE
    // =========================================================

    @Test
    void shouldReturn400WhenInvestorValidationFails()
            throws Exception {

        mockMvc.perform(
                        post(
                                "/api/auth/investor/register"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"AAA",
                                          "email":"invalid-email",
                                          "phoneNumber":"12345",
                                          "password":"abc",
                                          "panNumber":"ABC",
                                          "accountNumber":"123",
                                          "riskProfile":"MODERATE",
                                          "nominee":{
                                            "name":"BBB",
                                            "age":0,
                                            "gender":"OTHER",
                                            "relationship":"",
                                            "accountNumber":"123"
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
                        jsonPath("$.error")
                                .value(
                                        "Bad Request"
                                )
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Request validation failed."
                                )
                )
                .andExpect(
                        jsonPath("$.fieldErrors.name")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.email")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.phoneNumber")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.password")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.panNumber")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.accountNumber")
                                .exists()
                )
                .andExpect(
                        jsonPath(
                                "$.fieldErrors['nominee.name']"
                        )
                                .exists()
                )
                .andExpect(
                        jsonPath(
                                "$.fieldErrors['nominee.age']"
                        )
                                .exists()
                )
                .andExpect(
                        jsonPath(
                                "$.fieldErrors['nominee.gender']"
                        )
                                .exists()
                )
                .andExpect(
                        jsonPath(
                                "$.fieldErrors['nominee.relationship']"
                        )
                                .exists()
                )
                .andExpect(
                        jsonPath(
                                "$.fieldErrors['nominee.accountNumber']"
                        )
                                .exists()
                );


        /*
         * Invalid registration data is rejected by @Valid,
         * therefore the registration service must not execute.
         */
        verify(
                investorService,
                never()
        )
                .registerInvestor(
                        any(
                                InvestorRegistrationRequest.class
                        )
                );
    }


    // =========================================================
    // INVESTOR REGISTRATION - NOMINEE MISSING
    // =========================================================

    @Test
    void shouldReturn400WhenNomineeIsMissing()
            throws Exception {

        mockMvc.perform(
                        post(
                                "/api/auth/investor/register"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Bharath Kumar",
                                          "email":"bharath@example.com",
                                          "phoneNumber":"9876543210",
                                          "password":"Password@1",
                                          "panNumber":"ABCDE1234F",
                                          "accountNumber":"1234567890127654",
                                          "riskProfile":"MODERATE"
                                        }
                                        """)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        jsonPath("$.fieldErrors.nominee")
                                .exists()
                );


        verify(
                investorService,
                never()
        )
                .registerInvestor(
                        any(
                                InvestorRegistrationRequest.class
                        )
                );
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @Test
    void shouldLogoutAndInvalidateSession()
            throws Exception {

        MockHttpSession session =
                new MockHttpSession();


        session.setAttribute(
                "USER_ID",
                "INV001"
        );


        session.setAttribute(
                "ROLE",
                "INVESTOR"
        );


        mockMvc.perform(
                        post(
                                "/api/auth/logout"
                        )
                                .session(
                                        session
                                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Logout successful."
                                )
                );
    }
}