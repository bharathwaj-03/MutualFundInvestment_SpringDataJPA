package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void shouldStoreInheritedUserFields() {
        User user = new Admin();
        user.setUserId("ADM001"); user.setName("Deepak"); user.setEmail("deepak@gmail.com");
        user.setPhoneNumber("9876543210"); user.setPassword("hash"); user.setUserRole("ADMIN"); user.setAge(30);
        assertEquals("ADM001",user.getUserId()); assertEquals("Deepak",user.getName()); assertEquals("deepak@gmail.com",user.getEmail());
        assertEquals("9876543210",user.getPhoneNumber()); assertEquals("hash",user.getPassword()); assertEquals("ADMIN",user.getUserRole()); assertEquals(30,user.getAge());
    }
}
