package grocerPanel.Model;

public class User {
    private int userID;
    private String username;
    private String password;
    private String role;

    public User(int userID, String username, String role) {
        this.userID = userID;
        this.username = username;
        this.role = role;
    }

    public User(int userID, String username, String password, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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


}
