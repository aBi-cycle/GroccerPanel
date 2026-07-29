package grocerPanel.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeModel extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                WelcomeModel.class.getResource("/grocerPanel/welcome-view.fxml")
        );

        Scene scene = new Scene(
                fxmlLoader.load(),
                720,
                480
        );

        stage.setTitle("GrocerPanel");
        stage.setScene(scene);

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.show();
    }
}