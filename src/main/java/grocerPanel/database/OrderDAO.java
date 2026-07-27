package grocerPanel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import grocerPanel.Model.Order;
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
            e.printStackTrace();
        }


        return orders;
    }

    public static void updateOrder(Order order) {

        String sql = """
            UPDATE order
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
            e.printStackTrace();
        }
    }
}
