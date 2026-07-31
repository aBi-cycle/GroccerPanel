package grocerPanel.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the User model.
 * Covers the role logic behind Staff Login (US-01) and account roles.
 */
class UserTest {

    @Test
    void managerRoleIsRecognized() {
        User u = new User(1, "boss", "pass", "Manager");
        assertTrue(u.isManager());
        assertFalse(u.isEmployee());
    }

    @Test
    void employeeRoleIsRecognized() {
        User u = new User(2, "clerk", "pass", "Employee");
        assertTrue(u.isEmployee());
        assertFalse(u.isManager());
    }

    @Test
    void roleCheckIsCaseInsensitive() {
        User u = new User(3, "boss", "pass", "manager");
        assertTrue(u.isManager());
    }

    @Test
    void gettersReturnConstructorValues() {
        User u = new User(4, "alice", "secret", "Employee");
        assertEquals(4, u.getUserID());
        assertEquals("alice", u.getUsername());
        assertEquals("secret", u.getPassword());
        assertEquals("Employee", u.getRole());
    }

    @Test
    void logoutClearsUserState() {
        User u = new User(5, "bob", "pw", "Manager");
        u.logout();
        assertEquals(0, u.getUserID());
        assertNull(u.getUsername());
        assertNull(u.getPassword());
        assertNull(u.getRole());
        assertFalse(u.isManager());
    }
}
