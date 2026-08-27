package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class RedeemTransactionTest {
    @Test
    void shouldExecuteAndStoreType() {
        RedeemTransaction t=new RedeemTransaction();
        t.setTransactionType("REDEEM");
        assertEquals("REDEEM",t.getTransactionType());
        assertDoesNotThrow(t::executeTransaction);
    } }
