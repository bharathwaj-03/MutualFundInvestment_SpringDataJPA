package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PayableTest {
    @Test void shouldExecuteFunctionalInterface(){
        double[] captured={0};
        Payable p=amount->captured[0]=amount;
        p.processPayment(999);
        assertEquals(999,captured[0]);
    } }
