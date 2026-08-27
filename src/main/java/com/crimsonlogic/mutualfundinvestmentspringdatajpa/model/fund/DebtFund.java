package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.MutualFund;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Concrete mutual-fund entity representing the Debt Fund category.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@DiscriminatorValue("Debt Fund")
public class DebtFund extends MutualFund {

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public DebtFund() {
    }
}
