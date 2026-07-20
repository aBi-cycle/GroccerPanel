module groccerPanel {
    requires javafx.controls;
    requires javafx.fxml;


    opens groccerPanel to javafx.fxml;
    exports groccerPanel;
    exports groccerPanel.Controller;
    opens groccerPanel.Controller to javafx.fxml;
    exports groccerPanel.Model;
    opens groccerPanel.Model to javafx.fxml;
}