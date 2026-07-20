module edu.utsa.cs3443.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens groccerPanel to javafx.fxml;
    exports groccerPanel;
}