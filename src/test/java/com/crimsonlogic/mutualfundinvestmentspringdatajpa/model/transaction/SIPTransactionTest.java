package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class SIPTransactionTest {
    @Test
    void shouldExecuteAndStoreType() {
        SIPTransaction t=new SIPTransaction();
        t.setTransactionType("SIP");
        assertEquals("SIP",t.getTransactionType());
        assertDoesNotThrow(t::executeTransaction);
    } }
