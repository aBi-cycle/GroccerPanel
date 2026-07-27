package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {

    @FXML
    private Button CancelButton;

    @FXML
    private Button GoButton;

    @FXML
    private TextField pwdTbox;

    @FXML
    private TextField unameTbox;

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/welcome-view.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onGo(ActionEvent event) throws IOException {
        String uname = unameTbox.getText().trim();
        String pwd = pwdTbox.getText();
        //System.out.println("uname: " + uname +"\npwd: "+pwd);



        if (UserDAO.authenticate(uname, pwd)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, " + uname + "!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/main-page.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            alert.showAndWait();

            stage.setScene(scene);
            stage.show();
            stage.setTitle("GroccerPanel - Main Page");

            

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
            pwdTbox.clear();
            unameTbox.clear();
        }
    }

}
