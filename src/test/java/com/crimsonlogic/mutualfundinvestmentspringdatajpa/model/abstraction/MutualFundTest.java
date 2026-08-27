package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutualFundTest {
    @Test
    void shouldStoreFundFieldsAndCompareByName() {
        MutualFund a = new EquityFund();
        MutualFund b = new EquityFund();
        a.setFundId("FND001"); a.setFundName("Alpha Fund"); a.setFundCategory("Equity Fund");
        a.setFundHouse("SBI"); a.setRiskLevel("High"); a.setNav(500); a.setMinimumInvestment(500);
        a.setSipGainPerYear(12); a.setLumpSumGainPerYear(14); a.setFundCode("EQ001");
        b.setFundName("Beta Fund");
        assertEquals("FND001", a.getFundId());
        assertEquals("Alpha Fund", a.getFundName());
        assertEquals("Equity Fund", a.getFundCategory());
        assertEquals("SBI", a.getFundHouse());
        assertEquals("High", a.getRiskLevel());
        assertEquals(500, a.getNav());
        assertEquals(500, a.getMinimumInvestment());
        assertEquals(12, a.getSipGainPerYear());
        assertEquals(14, a.getLumpSumGainPerYear());
        assertEquals("EQ001", a.getFundCode());
        assertTrue(a.compareTo(b) < 0);
    }
}
