package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class NomineeTest {
    @Test
    void shouldStoreNomineeDetails(){
        Nominee n=new Nominee();
        n.setNomineeId("NOM001");
        n.setName("Rahul");
        n.setAge(30);
        n.setGender("MALE");
        n.setRelationship("BROTHER");
        n.setAccountNumber("12345");
        assertEquals("NOM001",n.getNomineeId());
        assertEquals("Rahul",n.getName());
        assertEquals(30,n.getAge());
        assertEquals("MALE",n.getGender());
        assertEquals("BROTHER",n.getRelationship());
        assertEquals("12345",n.getAccountNumber());
    }
}
