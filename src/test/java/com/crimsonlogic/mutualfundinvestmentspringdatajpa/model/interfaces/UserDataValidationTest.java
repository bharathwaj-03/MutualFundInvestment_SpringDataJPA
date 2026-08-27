package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UserDataValidationTest {
    @Test void shouldValidateUsingLambda() throws Exception {
        UserDataValidation v=s->s.trim().toUpperCase();
        assertEquals("BHARATH",v.validate(" bharath "));
    } }
