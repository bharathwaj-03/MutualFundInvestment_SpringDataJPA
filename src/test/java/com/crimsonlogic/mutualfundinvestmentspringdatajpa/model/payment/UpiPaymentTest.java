package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UpiPaymentTest {
    @Test void shouldAcceptValidUpiId() throws Exception
    {
        UpiPayment p=new UpiPayment();
        p.setUpiId("Bharath@okaxis");
        assertEquals("bharath@okaxis",p.getUpiId());
    }
    @Test void shouldRejectInvalidUpiId()
    { UpiPayment p=new UpiPayment();
        assertThrows(UserDataValidationException.class,()->p.setUpiId("bad"));
    } }
