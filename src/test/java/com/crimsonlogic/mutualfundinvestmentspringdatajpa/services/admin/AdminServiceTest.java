package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request.AdminProfileUpdateRequest;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .exception.ResourceNotFoundException;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .model.user.Admin;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.AdminRepository;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .utilities.security.PasswordUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;


    @InjectMocks
    private AdminService adminService;


    @Test
    void shouldAuthenticateAdminForCorrectPassword() {

        Admin admin =
                new Admin();

        admin.setUserId(
                "ADM001"
        );

        admin.setName(
                "Deepak"
        );

        admin.setPassword(
                PasswordUtil
                        .hashPassword(
                                "Deep@37"
                        )
        );


        when(
                adminRepository
                        .findById(
                                "ADM001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                admin
                        )
                );


        assertTrue(
                adminService
                        .authenticateAdmin(
                                "ADM001",
                                "Deep@37"
                        )
        );


        verify(
                adminRepository
        )
                .findById(
                        "ADM001"
                );
    }


    @Test
    void shouldRejectAuthenticationWhenAdminDoesNotExist() {

        when(
                adminRepository
                        .findById(
                                "ADM999"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertFalse(
                adminService
                        .authenticateAdmin(
                                "ADM999",
                                "Password@1"
                        )
        );
    }


    @Test
    void shouldRejectAuthenticationForWrongPassword() {

        Admin admin =
                new Admin();

        admin.setPassword(
                PasswordUtil
                        .hashPassword(
                                "Correct@1"
                        )
        );


        when(
                adminRepository
                        .findById(
                                "ADM001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                admin
                        )
                );


        assertFalse(
                adminService
                        .authenticateAdmin(
                                "ADM001",
                                "Wrong@1"
                        )
        );
    }


    @Test
    void shouldReturnAdminById() {

        Admin admin =
                new Admin();

        admin.setUserId(
                "ADM001"
        );


        when(
                adminRepository
                        .findById(
                                "ADM001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                admin
                        )
                );


        assertSame(
                admin,
                adminService
                        .getAdminByUserId(
                                "ADM001"
                        )
        );
    }


    @Test
    void shouldThrowWhenAdminDoesNotExist() {

        when(
                adminRepository
                        .findById(
                                "ADM404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        adminService
                                .getAdminByUserId(
                                        "ADM404"
                                )
        );
    }


    @Test
    void shouldUpdateValidAdminProfile() {

        Admin existingAdmin =
                new Admin();

        existingAdmin.setUserId(
                "ADM001"
        );

        existingAdmin.setName(
                "Old Name"
        );

        existingAdmin.setEmail(
                "old@gmail.com"
        );

        existingAdmin.setPhoneNumber(
                "9876543210"
        );

        existingAdmin.setAdminCode(
                "A001"
        );

        existingAdmin.setPassword(
                "HASHED_PASSWORD"
        );

        existingAdmin.setUserRole(
                "ADMIN"
        );


        AdminProfileUpdateRequest request =
                new AdminProfileUpdateRequest();

        request.setName(
                "Deepak Kumar"
        );

        request.setEmail(
                "deepak@gmail.com"
        );

        request.setPhoneNumber(
                "8909878678"
        );




        when(
                adminRepository
                        .findById(
                                "ADM001"
                        )
        )
                .thenReturn(
                        Optional.of(
                                existingAdmin
                        )
                );


        when(
                adminRepository
                        .save(
                                any(Admin.class)
                        )
        )
                .thenAnswer(
                        invocation ->
                                invocation
                                        .getArgument(0)
                );


        Admin updatedAdmin =
                adminService
                        .updateAdminProfile(
                                "ADM001",
                                request
                        );


        assertNotNull(
                updatedAdmin
        );

        assertEquals(
                "DEEPAK KUMAR",
                updatedAdmin.getName()
        );

        assertEquals(
                "deepak@gmail.com",
                updatedAdmin.getEmail()
        );

        assertEquals(
                "8909878678",
                updatedAdmin.getPhoneNumber()
        );

        assertEquals(
                "A001",
                updatedAdmin.getAdminCode()
        );


        /*
         * Protected values must remain unchanged during
         * a normal profile update.
         */
        assertEquals(
                "ADM001",
                updatedAdmin.getUserId()
        );

        assertEquals(
                "HASHED_PASSWORD",
                updatedAdmin.getPassword()
        );

        assertEquals(
                "ADMIN",
                updatedAdmin.getUserRole()
        );


        verify(
                adminRepository
        )
                .save(
                        existingAdmin
                );
    }


    @Test
    void shouldThrowWhenUpdatingMissingAdmin() {

        AdminProfileUpdateRequest request =
                new AdminProfileUpdateRequest();

        request.setName(
                "Deepak Kumar"
        );

        request.setEmail(
                "deepak@gmail.com"
        );

        request.setPhoneNumber(
                "8909878678"
        );




        when(
                adminRepository
                        .findById(
                                "ADM404"
                        )
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                ResourceNotFoundException.class,
                () ->
                        adminService
                                .updateAdminProfile(
                                        "ADM404",
                                        request
                                )
        );


        verify(
                adminRepository,
                never()
        )
                .save(
                        any(Admin.class)
                );
    }
}