package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for base user persistence and authentication-related lookups.
 *
 * Spring Data JPA derives the lookup queries from the repository method names.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by user ID and role.
     *
     * @param userId user ID to search for
     * @param userRole expected user role
     * @return matching user when present
     */
    Optional<User> findByUserIdAndUserRole(String userId, String userRole);

    /**
     * Finds a user by email address.
     *
     * @param email email address to search for
     * @return matching user when present
     */
    Optional<User> findByEmail(String email);
}
