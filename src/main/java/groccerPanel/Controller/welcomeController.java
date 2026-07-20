package groccerPanel.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;

public class welcomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/groccerPanel/login-view.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
