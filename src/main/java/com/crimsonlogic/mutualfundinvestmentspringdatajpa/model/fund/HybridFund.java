package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Concrete mutual-fund entity representing the Hybrid Fund category.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@DiscriminatorValue("Hybrid Fund")
public class HybridFund extends MutualFund {

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public HybridFund() {
    }
}
