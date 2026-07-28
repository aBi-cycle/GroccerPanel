package grocerPanel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import grocerPanel.Model.Discount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DiscountDAO {

    public static ObservableList<Discount> getAllDiscounts() {

        ObservableList<Discount> discounts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM productDiscountType";

        try(Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql)) {

            while(result.next()) {

                discounts.add(
                    new Discount(
                        result.getInt("discountID"),
                        result.getString("code"),
                        result.getString("discountType"),
                        result.getDouble("discountValue"),
                        result.getBoolean("active"),
                        result.getString("expirationDate")
                    )
                );
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return discounts;
    }

    public static boolean addDiscount(String code, String type, double value, String expirationDate) {

        String sql = """
            INSERT INTO productDiscountType
            (code, discountType, discountValue, active, expirationDate)
            VALUES (?, ?, ?, 1, ?)
            """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, type);
            ps.setDouble(3, value);
            ps.setString(4, expirationDate);

            ps.executeUpdate();
            return true;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static boolean updateDiscount(int discountID, String code, String type, double value, String expirationDate) {

        String sql = """
            UPDATE productDiscountType
            SET code = ?,
                discountType = ?,
                discountValue = ?,
                expirationDate = ?
            WHERE discountID = ?
            """;


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, code);
            ps.setString(2, type);
            ps.setDouble(3, value);
            ps.setString(4, expirationDate);
            ps.setInt(5, discountID);


            ps.executeUpdate();

            return true;


        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deleteDiscount(int discountID) {
        String deleteLinks =
            "DELETE FROM ProductDiscount WHERE discountID = ?";


        String deleteDiscount =
            "DELETE FROM productDiscountType WHERE discountID = ?";


        try(Connection conn = DatabaseConnection.getConnection()) {


            PreparedStatement ps1 =
                conn.prepareStatement(deleteLinks);

            ps1.setInt(1, discountID);
            ps1.executeUpdate();



            PreparedStatement ps2 =
                conn.prepareStatement(deleteDiscount);

            ps2.setInt(1, discountID);
            ps2.executeUpdate();


            return true;


        } catch(Exception e) {

            System.out.println(e.getMessage());
            return false;
        }
    }
}