package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.AdminRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    void shouldAuthenticateAdminForCorrectPassword() {
        Admin admin = new Admin();
        admin.setUserId("ADM001");
        admin.setName("Deepak");
        admin.setPassword(PasswordUtil.hashPassword("Deep@37"));

        when(adminRepository.findById("ADM001"))
                .thenReturn(Optional.of(admin));

        assertTrue(adminService.authenticateAdmin("ADM001", "Deep@37"));

        verify(adminRepository).findById("ADM001");
    }

    @Test
    void shouldRejectAuthenticationWhenAdminDoesNotExist() {
        when(adminRepository.findById("ADM999"))
                .thenReturn(Optional.empty());

        assertFalse(adminService.authenticateAdmin("ADM999", "Password@1"));
    }

    @Test
    void shouldRejectAuthenticationForWrongPassword() {
        Admin admin = new Admin();
        admin.setPassword(PasswordUtil.hashPassword("Correct@1"));

        when(adminRepository.findById("ADM001"))
                .thenReturn(Optional.of(admin));

        assertFalse(adminService.authenticateAdmin("ADM001", "Wrong@1"));
    }

    @Test
    void shouldReturnAdminById() {
        Admin admin = new Admin();
        admin.setUserId("ADM001");

        when(adminRepository.findById("ADM001"))
                .thenReturn(Optional.of(admin));

        assertSame(admin, adminService.getAdminByUserId("ADM001"));
    }

    @Test
    void shouldThrowWhenAdminDoesNotExist() {
        when(adminRepository.findById("ADM404"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.getAdminByUserId("ADM404")
        );
    }

    @Test
    void shouldUpdateValidAdminProfile() {
        Admin admin = new Admin();
        admin.setUserId("ADM001");
        admin.setName("Deepak");
        admin.setEmail("deepak@gmail.com");
        admin.setPhoneNumber("8909878678");
        admin.setAdminCode("A001");

        assertTrue(adminService.updateAdminProfile(admin));

        verify(adminRepository).save(admin);
    }

    @Test
    void shouldRejectInvalidAdminProfile() {
        Admin admin = new Admin();
        admin.setUserId("ADM001");
        admin.setName("");
        admin.setEmail("deepak@gmail.com");
        admin.setPhoneNumber("8909878678");
        admin.setAdminCode("A001");

        assertFalse(adminService.updateAdminProfile(admin));

        verify(adminRepository, never()).save(any());
    }
}
