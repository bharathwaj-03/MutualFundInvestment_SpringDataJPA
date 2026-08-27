package com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for nominee persistence operations.
 *
 * Standard CRUD behavior is inherited from {@link JpaRepository}.
 */
public interface NomineeRepository extends JpaRepository<Nominee, String> {
}
