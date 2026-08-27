package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.BuyTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    @Test
    void shouldStoreTransactionFields() {
        Transaction t = new BuyTransaction();
        Investor investor = new Investor();
        EquityFund fund = new EquityFund();
        Payment payment = new Payment();
        LocalDateTime time = LocalDateTime.of(2026,8,23,12,0);
        t.setTransactionId("TXN001"); t.setInvestor(investor); t.setMutualFund(fund); t.setPayment(payment);
        t.setAmount(1000); t.setTransactionDateTime(time); t.setTransactionStatus("SUCCESS"); t.setTransactionType("BUY");
        assertEquals("TXN001", t.getTransactionId()); assertSame(investor,t.getInvestor()); assertSame(fund,t.getMutualFund());
        assertSame(payment,t.getPayment()); assertEquals(1000,t.getAmount()); assertEquals(time,t.getTransactionDateTime());
        assertEquals("SUCCESS",t.getTransactionStatus()); assertEquals("BUY",t.getTransactionType());
    }
}
