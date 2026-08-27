package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .controller;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.AdminProfileUpdateRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.GlobalExceptionHandler;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.user.Admin;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.admin.I_AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet
        .request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet
        .result.MockMvcResultMatchers.*;


class AdminProfileControllerTest {

    @Mock
    private I_AdminService adminService;

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
                                new AdminProfileController(
                                        adminService
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


    @Test
    void shouldGetAdminProfile()
            throws Exception {

        Admin admin =
                new Admin();

        admin.setUserId(
                "ADM001"
        );

        admin.setName(
                "Deepak"
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
                        get(
                                "/api/admin/profile/ADM001"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value("ADM001")
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Deepak")
                );
    }


    @Test
    void shouldReturn404ForMissingAdmin()
            throws Exception {

        when(
                adminService
                        .getAdminByUserId(
                                "ADM404"
                        )
        )
                .thenReturn(
                        null
                );


        mockMvc.perform(
                        get(
                                "/api/admin/profile/ADM404"
                        )
                )
                .andExpect(
                        status().isNotFound()
                );
    }


    @Test
    void shouldUpdateAdminProfile()
            throws Exception {

        Admin updatedAdmin =
                new Admin();

        updatedAdmin.setUserId(
                "ADM001"
        );

        updatedAdmin.setName(
                "Deepak Kumar"
        );

        updatedAdmin.setEmail(
                "deepak@gmail.com"
        );

        updatedAdmin.setPhoneNumber(
                "9876543210"
        );

        updatedAdmin.setAdminCode(
                "A001"
        );


        when(
                adminService
                        .updateAdminProfile(
                                eq("ADM001"),
                                any(
                                        AdminProfileUpdateRequest.class
                                )
                        )
        )
                .thenReturn(
                        updatedAdmin
                );


        mockMvc.perform(
                        put(
                                "/api/admin/profile/ADM001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Deepak Kumar",
                                          "email":"deepak@gmail.com",
                                          "phoneNumber":"9876543210",
                                          "adminCode":"A001"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.userId")
                                .value("ADM001")
                )
                .andExpect(
                        jsonPath("$.name")
                                .value(
                                        "Deepak Kumar"
                                )
                );


        verify(
                adminService
        )
                .updateAdminProfile(
                        eq("ADM001"),
                        any(
                                AdminProfileUpdateRequest.class
                        )
                );
    }


    @Test
    void shouldReturn400ForInvalidAdminUpdate()
            throws Exception {

        mockMvc.perform(
                        put(
                                "/api/admin/profile/ADM001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"AAA",
                                          "email":"bad-email",
                                          "phoneNumber":"123",
                                          "adminCode":""
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
                adminService,
                never()
        )
                .updateAdminProfile(
                        anyString(),
                        any(
                                AdminProfileUpdateRequest.class
                        )
                );
    }
}