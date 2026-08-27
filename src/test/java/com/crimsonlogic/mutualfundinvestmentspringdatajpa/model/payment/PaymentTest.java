package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
class PaymentTest {
    @Test void shouldStorePaymentDetails(){
        Payment p=new Payment();
        Investor i=new Investor();
        LocalDateTime d=LocalDateTime.of(2026,8,23,10,0);
        p.setPaymentId("PAY001");
        p.setInvestor(i);
        p.setPaymentMethod("UPI");
        p.setUpiId("a@upi");
        p.setCardNumber("1111");
        p.setCardHolderName("A");
        p.setBankName("SBI");p.setAccountNumber("123");
        p.setPaymentStatus("SUCCESS");p.setPaymentDate(d);
        assertEquals("PAY001",p.getPaymentId());
        assertSame(i,p.getInvestor());
        assertEquals("UPI",p.getPaymentMethod());
        assertEquals("a@upi",p.getUpiId());
        assertEquals("1111",p.getCardNumber());
        assertEquals("A",p.getCardHolderName());
        assertEquals("SBI",p.getBankName());
        assertEquals("123",p.getAccountNumber());
        assertEquals("SUCCESS",p.getPaymentStatus());
        assertEquals(d,p.getPaymentDate());

    } }

