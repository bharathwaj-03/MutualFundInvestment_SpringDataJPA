package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.transaction.RedeemTransaction;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class RedemptionTest {
 @Test void shouldStoreRedemptionDetails(){ Redemption r=new Redemption(); Investor inv=new Investor(); EquityFund f=new EquityFund(); RedeemTransaction t=new RedeemTransaction();
 r.setRedemptionId("RED001"); r.setInvestor(inv); r.setMutualFund(f); r.setTransaction(t); r.setUnitsRedeemed(2); r.setNavAtRedemption(590); r.setGrossAmount(1180); r.setBrokerageCharges(10); r.setAmountReceived(1170);
 assertEquals("RED001",r.getRedemptionId()); assertSame(inv,r.getInvestor()); assertSame(f,r.getMutualFund()); assertSame(t,r.getTransaction()); assertEquals(2,r.getUnitsRedeemed()); assertEquals(590,r.getNavAtRedemption()); assertEquals(1180,r.getGrossAmount()); assertEquals(10,r.getBrokerageCharges()); assertEquals(1170,r.getAmountReceived()); }
}
