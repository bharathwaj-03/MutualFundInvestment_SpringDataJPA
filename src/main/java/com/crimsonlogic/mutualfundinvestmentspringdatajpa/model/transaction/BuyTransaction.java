package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.Transaction;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Concrete transaction used to record and execute a mutual-fund purchase.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@DiscriminatorValue("BUY")
public class BuyTransaction extends Transaction {

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public BuyTransaction() {
    }

    /**
     * Executes the behavior defined for this concrete transaction type.
     */
    @Override
    public void executeTransaction() {
        System.out.println("Buy Transaction Executed Successfully");
    }
}
