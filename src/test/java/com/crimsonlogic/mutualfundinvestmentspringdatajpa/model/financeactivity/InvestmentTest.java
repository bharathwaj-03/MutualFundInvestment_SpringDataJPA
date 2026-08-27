package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class InvestmentTest {
 @Test void shouldStoreInvestmentDetailsAndRelationships(){ Investment i=new Investment(); Investor inv=new Investor(); EquityFund f=new EquityFund();
 i.setInvestmentId("INVT001"); i.setInvestor(inv); i.setMutualFund(f); i.setUnitsPurchased(20); i.setInvestmentYears(5); i.setAssetGainPerYear(1400); i.setAssetGainTotalInvestedYears(7000);
 assertEquals("INVT001",i.getInvestmentId()); assertSame(inv,i.getInvestor()); assertSame(f,i.getMutualFund()); assertEquals(20,i.getUnitsPurchased()); assertEquals(5,i.getInvestmentYears()); assertEquals(1400,i.getAssetGainPerYear()); assertEquals(7000,i.getAssetGainTotalInvestedYears()); }
 @Test void equalInvestmentsShouldHaveSameHashCode(){ Investment a=new Investment(); Investment b=new Investment(); assertEquals(a,b); assertEquals(a.hashCode(),b.hashCode()); }
}
