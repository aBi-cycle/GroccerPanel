package grocerPanel.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderItems model (a single line item on an order).
 * Supports Execute Order stock decrement (US-11).
 */
class OrderItemsTest {

    @Test
    void constructorStoresAllFields() {
        OrderItems item = new OrderItems(7, "Eggs", 2.99, 3);
        assertEquals(7, item.getProductID());
        assertEquals("Eggs", item.getProductName());
        assertEquals(2.99, item.getPrice(), 0.0001);
        assertEquals(3, item.getQuantity());
    }

    @Test
    void lineTotalCanBeComputedFromPriceAndQuantity() {
        OrderItems item = new OrderItems(7, "Eggs", 2.50, 4);
        assertEquals(10.00, item.getPrice() * item.getQuantity(), 0.0001);
    }
}
