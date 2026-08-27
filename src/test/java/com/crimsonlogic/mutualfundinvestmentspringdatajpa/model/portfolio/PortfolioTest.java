package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import java.time.LocalDate; import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class PortfolioTest {
    @Test
    void shouldStorePortfolioAndHoldings(){
        Portfolio p=new Portfolio();
        Investor i=new Investor();
        Holding h=new Holding();
        LocalDate d=LocalDate.of(2026,8,23);
        p.setPortfolioId("PORT001");
        p.setInvestor(i);
        p.setHoldings(List.of(h));
        p.setLastActivityDate(d);
        assertEquals("PORT001",p.getPortfolioId());
        assertSame(i,p.getInvestor());assertEquals(1,p.getHoldings().size());
        assertSame(h,p.getHoldings().get(0));
        assertEquals(d,p.getLastActivityDate()); }
    @Test void newPortfolioShouldHaveEmptyHoldingList(){
        assertNotNull(new Portfolio().getHoldings());
        assertTrue(new Portfolio().getHoldings().isEmpty());
    } }
