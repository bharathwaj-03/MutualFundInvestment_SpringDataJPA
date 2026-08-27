package com.crimsonlogic.mutualfundinvestmentspringdatajpa.controller;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.AdminProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin.I_AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
