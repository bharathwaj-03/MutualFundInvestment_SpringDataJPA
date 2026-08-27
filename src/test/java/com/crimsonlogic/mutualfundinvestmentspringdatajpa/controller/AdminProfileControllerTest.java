package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin.I_AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminProfileControllerTest {

    @Mock
    private I_AdminService adminService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new AdminProfileController(
                                adminService
                        )
                )
                .build();
    }

    @Test
    void shouldGetAdminProfile()
            throws Exception {

        Admin admin =
                new Admin();

        admin.setUserId("ADM001");
        admin.setName("Deepak");

        when(adminService
                .getAdminByUserId("ADM001"))
                .thenReturn(admin);

        mockMvc.perform(
                        get(
                                "/api/admin/profile/ADM001"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId")
                        .value("ADM001"))
                .andExpect(jsonPath("$.name")
                        .value("Deepak"));
    }

    @Test
    void shouldReturn404ForMissingAdmin()
            throws Exception {

        when(adminService
                .getAdminByUserId("ADM404"))
                .thenReturn(null);

        mockMvc.perform(
                        get(
                                "/api/admin/profile/ADM404"
                        )
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenAdminUpdateFails()
            throws Exception {

        when(adminService
                .updateAdminProfile(any(Admin.class)))
                .thenReturn(false);

        mockMvc.perform(
                        put(
                                "/api/admin/profile/ADM001"
                        )
                                .contentType(
                                        "application/json"
                                )
                                .content("""
                                        {
                                          "name":"Deepak"
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest());
    }
}
