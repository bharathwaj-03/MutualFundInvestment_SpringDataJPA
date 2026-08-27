package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class EquityFundTest {
    @Test void shouldCreateAndUseFund() {
    EquityFund fund=new EquityFund();
    fund.setFundId("FND001");
    fund.setFundName("Test Equity Fund");
    fund.setFundCategory("Equity Fund");
    fund.setNav(500);
    assertEquals("FND001",fund.getFundId());
    assertEquals("Test Equity Fund",fund.getFundName());
    assertEquals("Equity Fund",fund.getFundCategory());
    assertEquals(500,fund.getNav()); } }
