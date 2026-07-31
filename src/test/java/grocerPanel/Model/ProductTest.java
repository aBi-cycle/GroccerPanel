package grocerPanel.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Product model.
 * Covers Add New Product (US-02) and Update Product (US-03) field handling.
 */
class ProductTest {

    private Product sample() {
        return new Product(1, "Milk", "1 gallon whole milk", 3.99, 10, "images/milk.jpg");
    }

    @Test
    void constructorStoresAllFields() {
        Product p = sample();
        assertEquals(1, p.getProductID());
        assertEquals("Milk", p.getName());
        assertEquals("1 gallon whole milk", p.getDescription());
        assertEquals(3.99, p.getPrice(), 0.0001);
        assertEquals(10, p.getQuantity());
        assertEquals("images/milk.jpg", p.getImagePath());
    }

    @Test
    void updatingPriceAndQuantityPersists() {
        Product p = sample();
        p.setPrice(2.49);
        p.setQuantity(25);
        assertEquals(2.49, p.getPrice(), 0.0001);
        assertEquals(25, p.getQuantity());
    }

    @Test
    void updatingNameAndDescriptionPersists() {
        Product p = sample();
        p.setName("Almond Milk");
        p.setDescription("unsweetened");
        assertEquals("Almond Milk", p.getName());
        assertEquals("unsweetened", p.getDescription());
    }

    @Test
    void outOfStockIsRepresentedByZeroQuantity() {
        Product p = sample();
        p.setQuantity(0);
        assertEquals(0, p.getQuantity());
    }
}
