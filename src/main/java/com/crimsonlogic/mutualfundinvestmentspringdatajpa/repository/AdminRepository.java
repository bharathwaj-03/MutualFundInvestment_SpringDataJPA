package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for administrator persistence and lookup operations.
 *
 * Spring Data JPA provides the implementation at runtime and supplies
 * standard CRUD operations for {@link Admin} entities.
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

    /**
     * Finds an administrator by user ID and role.
     *
     * @param userId administrator user ID
     * @param userRole expected user role
     * @return matching administrator when present
     */
    Optional<Admin> findByUserIdAndUserRole(String userId, String userRole);

    /**
     * Finds an administrator using the unique administrator code.
     *
     * @param adminCode administrator code to search for
     * @return matching administrator when present
     */
    Optional<Admin> findByAdminCode(String adminCode);
}
