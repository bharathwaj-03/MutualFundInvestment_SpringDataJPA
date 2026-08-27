package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class BuyTransactionTest {
    @Test
    void shouldExecuteAndStoreType() {
        BuyTransaction t=new BuyTransaction();
        t.setTransactionType("BUY");
        assertEquals("BUY",t.getTransactionType());
        assertDoesNotThrow(t::executeTransaction);
    } }
