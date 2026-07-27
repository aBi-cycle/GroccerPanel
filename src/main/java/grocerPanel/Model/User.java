package grocerPanel.Model;

import grocerPanel.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int userID;
    private String username;
    private String role;

    public User(int userID, String username, String role) {
        this.userID = userID;
        this.username = username;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isManager() {
        return "Manager".equalsIgnoreCase(role);
    }

    public boolean isEmployee() {
        return "Employee".equalsIgnoreCase(role);
    }

    public static User login(String username, String password) {
        String sql = "SELECT userID, username, role FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("userID");
                    String user = rs.getString("username");
                    String role = rs.getString("role");
                    return new User(id, user, role);
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }
}
