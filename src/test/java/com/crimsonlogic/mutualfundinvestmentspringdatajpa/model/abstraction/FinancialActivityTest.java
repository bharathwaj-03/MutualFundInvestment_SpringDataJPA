package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.financeactivity.Investment;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FinancialActivityTest {
    @Test
    void shouldStoreInheritedActivityFields() {
        Investment activity = new Investment();
        LocalDate date = LocalDate.of(2026, 8, 23);
        activity.setActivityId(10L);
        activity.setAmount(10000.0);
        activity.setActivityDate(date);
        assertEquals(10L, activity.getActivityId());
        assertEquals(10000.0, activity.getAmount());
        assertEquals(date, activity.getActivityDate());
    }
}
