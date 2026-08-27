package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;
import org.junit.jupiter.api.Test; import java.time.LocalDate; import static org.junit.jupiter.api.Assertions.*;
class InvestorTest {
    @Test
    void shouldStoreInvestorDetailsAndNominee(){
        Investor i=new Investor(); Nominee n=new Nominee();
        LocalDate d=LocalDate.of(2026,8,23);
        i.setUserId("INV001");
        i.setName("Bharath");
        i.setPanNumber("ABCDE1234F");
        i.setAccountNumber("12345");
        i.setRegistrationDate(d);
        i.setRiskProfile("MODERATE");
        i.setActive(true);
        i.setNominee(n);
        assertEquals("INV001",i.getUserId());
        assertEquals("Bharath",i.getName());
        assertEquals("ABCDE1234F",i.getPanNumber());
        assertEquals("12345",i.getAccountNumber());
        assertEquals(d,i.getRegistrationDate());
        assertEquals("MODERATE",i.getRiskProfile())
        ;assertTrue(i.isActive())
        ;assertSame(n,i.getNominee()); }
    @Test
    void shouldCompareInvestorsByUserId(){
        Investor a=new Investor();
        Investor b=new Investor();
        a.setUserId("INV001");
        b.setUserId("INV002");
        assertTrue(a.compareTo(b)<0);
    } }
