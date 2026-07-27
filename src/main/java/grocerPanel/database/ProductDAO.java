package grocerPanel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import grocerPanel.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ProductDAO {


    public static ObservableList<Product> getAllProducts() {

        ObservableList<Product> products =
                FXCollections.observableArrayList();


        String sql = """
                SELECT productID,
                       name,
                       description,
                       price,
                       quantity,
                       ImagePath
                FROM product;
                """;


        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {

                Product product = new Product(
                    rs.getInt("productID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("ImagePath")
                );


                products.add(product);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return products;
    }

    public static void updateProduct(Product product) {

        String sql = """
            UPDATE product
            SET name = ?,
                description = ?,
                price = ?,
                quantity = ?
            WHERE productID = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getProductID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}