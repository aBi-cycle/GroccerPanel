package grocerPanel.Model;


public class OrderItems {
    private int productID;
    private String productName;
    private double price;
    private int quantity;

    public OrderItems(int productID, String productName, double price, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }


}

