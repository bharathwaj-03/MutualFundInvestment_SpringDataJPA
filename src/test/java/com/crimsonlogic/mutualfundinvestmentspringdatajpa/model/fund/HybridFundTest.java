package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HybridFundTest {
    @Test void shouldCreateAndUseFund() { HybridFund fund=new HybridFund();
    fund.setFundId("FND001");
    fund.setFundName("Test Hybrid Fund");
    fund.setFundCategory("Hybrid Fund");
    fund.setNav(500);
    assertEquals("FND001",fund.getFundId());
    assertEquals("Test Hybrid Fund",fund.getFundName());
    assertEquals("Hybrid Fund",fund.getFundCategory());
    assertEquals(500,fund.getNav());
    } }

