package grocerPanel.database;

import grocerPanel.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
