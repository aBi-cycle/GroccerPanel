package grocerPanel.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Order model.
 * Covers View Order Details (US-10) and the status change on Execute Order (US-11).
 */
class OrderTest {

    private Order sample() {
        return new Order(100, "2026-07-29", "Jane Doe", 42.50, "Pending");
    }

    @Test
    void constructorStoresAllFields() {
        Order o = sample();
        assertEquals(100, o.getOrderID());
        assertEquals("2026-07-29", o.getOrderDate());
        assertEquals("Jane Doe", o.getCustomerName());
        assertEquals(42.50, o.getTotalAmount(), 0.0001);
        assertEquals("Pending", o.getStatus());
    }

    @Test
    void executingAnOrderChangesStatusToCompleted() {
        Order o = sample();
        o.setStatus("Completed");
        assertEquals("Completed", o.getStatus());
    }

    @Test
    void settersUpdateCustomerAndTotal() {
        Order o = sample();
        o.setCustomerName("John Smith");
        o.setTotalAmount(99.99);
        assertEquals("John Smith", o.getCustomerName());
        assertEquals(99.99, o.getTotalAmount(), 0.0001);
    }
}
