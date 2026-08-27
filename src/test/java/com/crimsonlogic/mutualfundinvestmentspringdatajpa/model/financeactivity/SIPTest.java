package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
class SIPTest {
 @Test void shouldStoreSipDetails(){
  SIP s=new SIP();
  Investor inv=new Investor();
  EquityFund f=new EquityFund();
  LocalDate start=LocalDate.of(2026,9,1);
 s.setSipId("SIP001");
 s.setInvestor(inv);
 s.setMutualFund(f);
 s.setMonthlyAmount(5000);
 s.setUnitsPurchased(10);
 s.setActivityDate(LocalDate.of(2026,8,23));
 s.setStartDate(start);
 s.setNextInstallmentDate(start.plusMonths(1));
 s.setInvestmentYears(5);
 s.setAssetGainPerYear(600);
 s.setAssetGainTotalInvestedYears(3000);
 s.setSipStatus("ACTIVE");
 assertEquals("SIP001",s.getSipId());
 assertSame(inv,s.getInvestor());
 assertSame(f,s.getMutualFund());
 assertEquals(5000,s.getMonthlyAmount());
 assertEquals(10,s.getUnitsPurchased());
 assertEquals(start,s.getStartDate());
 assertEquals(start.plusMonths(1),s.getNextInstallmentDate());
 assertEquals(5,s.getInvestmentYears());
 assertEquals("ACTIVE",s.getSipStatus()); }
}
