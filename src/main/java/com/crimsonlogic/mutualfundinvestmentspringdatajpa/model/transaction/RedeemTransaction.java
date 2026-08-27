package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Concrete transaction used to record and execute a mutual-fund redemption.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@DiscriminatorValue("REDEEM")
public class RedeemTransaction extends Transaction {

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public RedeemTransaction() {
    }

    /**
     * Executes the behavior defined for this concrete transaction type.
     */
    @Override
    public void executeTransaction() {
        System.out.println("Redeem Transaction Executed Successfully");
    }
}
