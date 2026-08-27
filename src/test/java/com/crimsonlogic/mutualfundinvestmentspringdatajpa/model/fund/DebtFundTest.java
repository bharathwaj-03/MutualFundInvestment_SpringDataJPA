package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DebtFundTest { @Test
void shouldCreateAndUseFund() {
    DebtFund fund=new DebtFund();
    fund.setFundId("FND001");
    fund.setFundName("Test Debt Fund");
    fund.setFundCategory("Debt Fund");
    fund.setNav(500);
    assertEquals("FND001",fund.getFundId());
    assertEquals("Test Debt Fund",fund.getFundName());
    assertEquals("Debt Fund",fund.getFundCategory());
    assertEquals(500,fund.getNav());
} }
