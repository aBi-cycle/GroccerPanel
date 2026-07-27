package grocerPanel.database;

import grocerPanel.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private UserDAO() {}

    public static User authenticate(String username, String password) {
        String sql = """
                SELECT userID, username, role
                FROM user
                WHERE username = ? AND password = ?
                LIMIT 1
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("userID");
                String name = rs.getString("username");
                String role = rs.getString("role");
                return new User(id, name, role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getNextUserID() {
        String sql = "SELECT COALESCE(MAX(userID), 0) + 1 AS nextID FROM user";

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

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT userID, username, role FROM user";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean addUser(String username, String password, String role) {
        String sql = "INSERT INTO user (userID, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getNextUserID());
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(int userID, String username, String password, String role) {
        String sql = "UPDATE user SET username = ?, password = ?, role = ? WHERE userID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setInt(4, userID);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(int userID) {
        String sql = "DELETE FROM user WHERE userID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
