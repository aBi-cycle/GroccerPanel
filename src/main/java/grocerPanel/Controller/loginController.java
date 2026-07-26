package grocerPanel.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        String uname = unameTbox.getText();
        String pwd = pwdTbox.getText();
        //System.out.println("uname: " + uname +"\npwd: "+pwd);

        if (uname.equals("temp")){
            if (pwd.equals("test")) {
                //TEST LOGIN SUCCESS

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("LOGIN SUCCESS");
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + uname + "!");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/main-page.fxml"));
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();

                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("LOGIN FAILURE");
                alert.setHeaderText(null);
                alert.setContentText("Sorry " + uname + ". That's the wrong password");
                alert.showAndWait();
                pwdTbox.setText("");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("LOGIN FAILURE");
            alert.setHeaderText(null);
            alert.setContentText("Sorry. That's the wrong username or password");
            alert.showAndWait();
            pwdTbox.setText("");
        }
    }

}
