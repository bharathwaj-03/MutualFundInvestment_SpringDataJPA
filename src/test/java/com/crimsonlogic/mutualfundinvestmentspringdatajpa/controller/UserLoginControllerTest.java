package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin.I_AdminService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor.I_InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserLoginControllerTest {

    @Mock
    private I_AdminService adminService;

    @Mock
    private I_InvestorService investorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new UserLoginController(
                                adminService,
                                investorService
                        )
                )
                .build();
    }

    @Test
    void shouldLoginAdminAndCreateSession() throws Exception {

        Admin admin = new Admin();
        admin.setUserId("ADM001");

        when(adminService.authenticateAdmin(
                "ADM001",
                "Deep@37"
        )).thenReturn(true);

        when(adminService.getAdminByUserId("ADM001"))
                .thenReturn(admin);

        mockMvc.perform(
                        post("/api/auth/admin/login")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "userId":"ADM001",
                                          "password":"Deep@37"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Admin login successful."))
                .andExpect(jsonPath("$.userId")
                        .value("ADM001"))
                .andExpect(jsonPath("$.role")
                        .value("ADMIN"))
                .andExpect(request()
                        .sessionAttribute(
                                "USER_ID",
                                "ADM001"
                        ))
                .andExpect(request()
                        .sessionAttribute(
                                "ROLE",
                                "ADMIN"
                        ));
    }

    @Test
    void shouldReturn401ForInvalidAdminLogin()
            throws Exception {

        when(adminService.authenticateAdmin(
                "ADM001",
                "Wrong"
        )).thenReturn(false);

        mockMvc.perform(
                        post("/api/auth/admin/login")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "userId":"ADM001",
                                          "password":"Wrong"
                                        }
                                        """)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message")
                        .value(
                                "Invalid Admin ID or Password."
                        ));
    }

    @Test
    void shouldLoginInvestorAndCreateSession()
            throws Exception {

        Investor investor = new Investor();
        investor.setUserId("INV001");
        investor.setName("Bharath");

        when(investorService.authenticateInvestor(
                "INV001",
                "Password@1"
        )).thenReturn(investor);

        mockMvc.perform(
                        post("/api/auth/investor/login")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "userId":"INV001",
                                          "password":"Password@1"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId")
                        .value("INV001"))
                .andExpect(jsonPath("$.name")
                        .value("Bharath"))
                .andExpect(jsonPath("$.role")
                        .value("INVESTOR"))
                .andExpect(request()
                        .sessionAttribute(
                                "USER_ID",
                                "INV001"
                        ))
                .andExpect(request()
                        .sessionAttribute(
                                "ROLE",
                                "INVESTOR"
                        ));
    }

    @Test
    void shouldReturn401ForInvalidInvestorLogin()
            throws Exception {

        when(investorService.authenticateInvestor(
                "INV001",
                "Wrong"
        )).thenReturn(null);

        mockMvc.perform(
                        post("/api/auth/investor/login")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "userId":"INV001",
                                          "password":"Wrong"
                                        }
                                        """)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message")
                        .value(
                                "Invalid Investor ID or Password."
                        ));
    }

    @Test
    void shouldRegisterInvestor()
            throws Exception {

        when(investorService.validateInvestor(
                any(Investor.class)
        )).thenReturn(Collections.emptyMap());

        when(investorService.registerInvestor(
                any(Investor.class)
        )).thenAnswer(invocation -> {

            Investor investor =
                    invocation.getArgument(0);

            investor.setUserId("INV001");

            return true;
        });

        mockMvc.perform(
                        post("/api/auth/investor/register")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "name":"Bharath",
                                          "email":"bharath@example.com",
                                          "phoneNumber":"9876543210",
                                          "password":"Password@1"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value(
                                "Investor registered successfully."
                        ))
                .andExpect(jsonPath("$.userId")
                        .value("INV001"));
    }

    @Test
    void shouldReturn400WhenInvestorValidationFails()
            throws Exception {

        Investor investor = new Investor();

        investor.setName("Ben");

        investor.setPassword("abc");


        Map<String, String> errors =
                new LinkedHashMap<>();

        errors.put(
                "name",
                "Name must contain at least 4 characters."
        );

        errors.put(
                "password",
                "Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one digit and one special character."
        );


        when(
                investorService.validateInvestor(
                        any(Investor.class)
                )
        )
                .thenReturn(errors);


        mockMvc.perform(
                        post(
                                "/api/auth/investor/register"
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        investor
                                                )
                                )
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
                                .value("Bad Request")
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Request validation failed."
                                )
                )
                .andExpect(
                        jsonPath("$.fieldErrors.name")
                                .value(
                                        "Name must contain at least 4 characters."
                                )
                )
                .andExpect(
                        jsonPath("$.fieldErrors.password")
                                .value(
                                        "Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one digit and one special character."
                                )
                );
    }

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
                        post("/api/auth/logout")
                                .session(session)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Logout successful."));
    }
}
