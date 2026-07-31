package grocerPanel.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Discount model.
 * Covers Create Discount (US-06) field handling.
 */
class DiscountTest {

    private Discount sample() {
        return new Discount(1, "CLEAR10", "PERCENT", 10.0, true, "2026-12-31");
    }

    @Test
    void constructorStoresAllFields() {
        Discount d = sample();
        assertEquals(1, d.getDiscountID());
        assertEquals("CLEAR10", d.getCode());
        assertEquals("PERCENT", d.getDiscountType());
        assertEquals(10.0, d.getDiscountValue(), 0.0001);
        assertTrue(d.getActive());
        assertEquals("2026-12-31", d.getExpirationDate());
    }

    @Test
    void discountCodeAndValueCanBeUpdated() {
        Discount d = sample();
        d.setCode("DAMAGED25");
        d.setDiscountValue(25.0);
        assertEquals("DAMAGED25", d.getCode());
        assertEquals(25.0, d.getDiscountValue(), 0.0001);
    }
}
