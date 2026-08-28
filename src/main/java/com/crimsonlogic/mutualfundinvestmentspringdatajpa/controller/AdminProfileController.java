package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.AdminProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.PasswordUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin.I_AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * REST controller that exposes administrator profile retrieval and update APIs.
 */
@RestController
@RequestMapping("/api/admin/profile")
public class AdminProfileController {

    private final I_AdminService adminService;

    /**
     * Creates this controller with the dependencies required to handle its HTTP operations.
     *
     * @param adminService value supplied to this endpoint
     */
    public AdminProfileController(I_AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Returns profile information for the requested identifier.
     *
     * @param adminId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getProfile(@PathVariable String adminId) {
        Admin admin = adminService.getAdminByUserId(adminId);
        return admin == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(admin);
    }

    /**
     * Updates profile information for the requested identifier.
     *
     * @param adminId value supplied to this endpoint
     * @return HTTP response or data produced by the endpoint
     */
    @PutMapping("/{adminId}")
    public ResponseEntity<Admin>
    updateProfile(
            @PathVariable
            String adminId,

            @Valid
            @RequestBody
            AdminProfileUpdateRequest request) {


        return ResponseEntity.ok(
                adminService
                        .updateAdminProfile(
                                adminId,
                                request
                        )
        );
    }
    /**
     * Updates the password of the requested administrator.
     *
     * @param adminId administrator identifier
     * @param request validated password information
     * @return password update confirmation
     */
    @PatchMapping("/{adminId}/password")
    public ResponseEntity<Map<String, Object>>
    updatePassword(
            @PathVariable
            String adminId,

            @Valid
            @RequestBody
            PasswordUpdateRequest request) {


        adminService
                .updateAdminPassword(
                        adminId,
                        request.getNewPassword()
                );


        Map<String, Object> response =
                new LinkedHashMap<>();


        response.put(
                "message",
                "Admin password updated successfully."
        );


        response.put(
                "adminId",
                adminId
        );


        return ResponseEntity.ok(
                response
        );
    }
}
