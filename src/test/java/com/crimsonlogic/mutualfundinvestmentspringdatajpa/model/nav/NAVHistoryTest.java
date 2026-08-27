package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.nav;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.fund.EquityFund;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
class NAVHistoryTest {
    @Test void shouldStoreNavHistory(){
        NAVHistory h=new NAVHistory();
        EquityFund f=new EquityFund();
        LocalDate d=LocalDate.of(2026,8,23);
        h.setHistoryId("NAV001");
        h.setMutualFund(f);
        h.setOldNav(500);
        h.setNewNav(510);
        h.setChangeDate(d);
        h.setChangedBy("ADM001");
        assertEquals("NAV001",h.getHistoryId());
        assertSame(f,h.getMutualFund());
        assertEquals(500,h.getOldNav());
        assertEquals(510,h.getNewNav());
        assertEquals(d,h.getChangeDate());
        assertEquals("ADM001",h.getChangedBy());
    } }
