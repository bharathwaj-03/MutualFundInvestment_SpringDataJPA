package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;
import org.junit.jupiter.api.Test; import java.time.LocalDate; import static org.junit.jupiter.api.Assertions.*;
class AdminTest {
    @Test
    void shouldStoreAdminDetails(){
        Admin a=new Admin();
        LocalDate d=LocalDate.of(2026,8,23);
        a.setUserId("ADM001");
        a.setName("Deepak");a.setAdminCode("A001");
        a.setCreatedDate(d);
        assertEquals("ADM001",a.getUserId());
        assertEquals("Deepak",a.getName());
        assertEquals("A001",a.getAdminCode());
        assertEquals(d,a.getCreatedDate()); } }
