package grocerPanel.Controller;

import java.io.IOException;
import java.util.Optional;

import grocerPanel.Model.User;
import grocerPanel.database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class ManagerController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> roleBox;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Manager", "Employee");
        roleBox.setValue("Employee");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsers();

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedUser) -> {
            if (selectedUser != null) {
                usernameField.setText(selectedUser.getUsername());
                passwordField.clear();
                roleBox.setValue(selectedUser.getRole());
            }
        });
    }

    private void loadUsers() {
        userTable.setItems(UserDAO.getAllUsers());
    }

    @FXML
    void onAddUser(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Missing Info", "Please fill in all fields.");
            return;
        }

        if (UserDAO.addUser(username, password, role)) {
            showAlert("Success", "User added.");
            usernameField.clear();
            passwordField.clear();
            roleBox.setValue("Employee");
            loadUsers();
        } else {
            showAlert("Error", "Could not add user.");
        }
    }

    @FXML
    void onUpdateUser(ActionEvent event) {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Select a user to update.");
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Missing Info", "Please fill in all fields.");
            return;
        }

        if (UserDAO.updateUser(selected.getUserID(), username, password, role)) {
            showAlert("Success", "User updated.");
            usernameField.clear();
            passwordField.clear();
            roleBox.setValue("Employee");
            loadUsers();
        } else {
            showAlert("Error", "Could not update user.");
        }
    }

    @FXML
    void onDeleteUser(ActionEvent event) {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Select a user to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete User");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete " + selected.getUsername() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (UserDAO.deleteUser(selected.getUserID())) {
                showAlert("Deleted", "User deleted.");
                usernameField.clear();
                passwordField.clear();
                roleBox.setValue("Employee");
                loadUsers();
            } else {
                showAlert("Error", "Could not delete user.");
            }
        }
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/main-page.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
