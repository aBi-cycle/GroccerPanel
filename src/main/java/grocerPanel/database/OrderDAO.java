package grocerPanel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import grocerPanel.Model.Order;
import grocerPanel.Model.OrderItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDAO {

    public static ObservableList<Order> getAllOrders() {

        ObservableList<Order> orders = FXCollections.observableArrayList();


        String sql = """
                SELECT orderID,
                       orderDate,
                       customerName,
                       totalAmount,
                       status
                FROM orders;
                """;


        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("orderID"),
                    rs.getString("orderDate"),
                    rs.getString("customerName"),
                    rs.getDouble("totalAmount"),
                    rs.getString("status")
                );


                orders.add(order);
            }


        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }


        return orders;
    }

    public static void updateOrder(Order order) {

        String sql = """
            UPDATE orders
            SET orderDate = ?,
                customerName = ?,
                totalAmount = ?,
                status = ?
                WHERE orderID = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getOrderDate());
            stmt.setString(2, order.getCustomerName());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getOrderID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }
    }

    public static int getNextOrderID() {
        String sql = "SELECT COALESCE(MAX(orderID), 0) + 1 AS nextID FROM orders";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("nextID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static boolean addOrder(Order order) {
        String sql = """
        INSERT INTO orders (orderID, orderDate, customerName, totalAmount, status)
        VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getOrderID());
            stmt.setString(2, order.getOrderDate());
            stmt.setString(3, order.getCustomerName());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.setString(5, order.getStatus());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(int orderID) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM order_items WHERE orderID = ?")) {
                stmt.setInt(1, orderID);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE orderID = ?")) {
                stmt.setInt(1, orderID);
                stmt.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<OrderItems> getOrderItems(int orderID) {
        ObservableList<OrderItems> items = FXCollections.observableArrayList();

        String sql = """
            SELECT productID, productName, price, quantity
            FROM order_items
            WHERE orderID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new OrderItems(
                            rs.getInt("productID"),
                            rs.getString("productName"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static void saveOrderItems(int orderID, List<OrderItems> items) {

        String updateSQL = """
            UPDATE order_items
            SET quantity = ?
            WHERE orderID = ? AND productID = ?
            """;


        String insertSQL = """
            INSERT INTO order_items
            (orderID, productID, quantity)
            VALUES (?, ?, ?)
            """;


        String deleteSQL = """
            DELETE FROM order_items
            WHERE orderID = ? AND productID = ?
            """;


        try(Connection connection = DatabaseConnection.getConnection()) {


            // Get current database items
            ObservableList<OrderItems> existingItems = getOrderItems(orderID);


            // Delete items no longer selected
            for(OrderItems oldItem : existingItems) {

                boolean stillExists = items.stream().anyMatch(newItem ->newItem.getProductID() == oldItem.getProductID());


                if(!stillExists) {
                    PreparedStatement delete = connection.prepareStatement(deleteSQL);
                    delete.setInt(1, orderID);
                    delete.setInt(2, oldItem.getProductID());
                    delete.executeUpdate();
                }
            }



            // Update existing or insert new
            for(OrderItems item : items) {

                boolean exists = existingItems.stream().anyMatch(oldItem ->oldItem.getProductID() == item.getProductID());

                if(exists) {

                    PreparedStatement update = connection.prepareStatement(updateSQL);

                    update.setInt(1, item.getQuantity());
                    update.setInt(2, orderID);
                    update.setInt(3, item.getProductID());

                    update.executeUpdate();

                } else {

                    PreparedStatement insert = connection.prepareStatement(insertSQL);

                    insert.setInt(1, orderID);
                    insert.setInt(2, item.getProductID());
                    insert.setInt(3, item.getQuantity());

                    insert.executeUpdate();
                }
            }


        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean executeOrder(Order order) {
        for (OrderItems item : getOrderItems(order.getOrderID())) {
            ProductDAO.decrementQuantity(item.getProductID(), item.getQuantity());
        }

        order.setStatus("Completed");
        OrderDAO.updateOrder(order);
        return true;
    }
}