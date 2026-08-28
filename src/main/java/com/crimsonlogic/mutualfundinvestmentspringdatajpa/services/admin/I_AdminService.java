package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.AdminProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;

/**
 * Defines administrator-related business operations exposed by the service layer.
 * Implementations provide the business rules while controllers depend on this contract rather than concrete service classes.
 */

public interface I_AdminService {

    /**
     * Authenticates an administrator by verifying the supplied password against the stored password hash.
     *
     * @param userId user identifier
     * @param password plain password supplied for verification
     * @return true when the operation succeeds; otherwise false
     */

    boolean authenticateAdmin(
            String userId,
            String password
    );

    /**
     * Retrieves an administrator by user ID and reports a not-found condition when the ID is absent.
     *
     * @param userId user identifier
     * @return result of the business operation
     */

    Admin getAdminByUserId(
            String userId
    );

    /**
     * Updates editable administrator profile information.
     *
     * @param adminId administrator information
     * @return true when the operation succeeds; otherwise false
     */

    Admin updateAdminProfile(
            String adminId,
            AdminProfileUpdateRequest request
    );

    /**
     * Updates the password of an existing administrator.
     *
     * @param adminId administrator identifier
     * @param newPassword new plain password to be hashed
     */
    void updateAdminPassword(
            String adminId,
            String newPassword
    );
}