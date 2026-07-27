package grocerPanel.Model;

public class Order {
    private int orderID;
    private String orderDate;
    private String customerName;
    private double totalAmount;
    private String status;

    public Order(int orderID, String orderDate, String customerName, double totalAmount, String status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
