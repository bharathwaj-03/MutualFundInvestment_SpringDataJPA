package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HoldingTest {
    @Test
    void shouldStoreHoldingDetails(){
        Holding h=new Holding();
        Portfolio p=new Portfolio();
        EquityFund f=new EquityFund();
        h.setHoldingId("HLD001");
        h.setPortfolio(p);
        h.setMutualFund(f);
        h.setUnitsOwned(20);
        h.setInvestedAmount(10000);
        h.setAverageNav(500);
        assertEquals("HLD001",h.getHoldingId());
        assertSame(p,h.getPortfolio());
        assertSame(f,h.getMutualFund());
        assertEquals(20,h.getUnitsOwned());
        assertEquals(10000,h.getInvestedAmount());
        assertEquals(500,h.getAverageNav());
    } }
