module grocerPanel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens grocerPanel to javafx.fxml;
    exports grocerPanel;
    exports grocerPanel.Controller;
    opens grocerPanel.Controller to javafx.fxml;
    exports grocerPanel.Model;
    opens grocerPanel.Model to javafx.fxml;
}