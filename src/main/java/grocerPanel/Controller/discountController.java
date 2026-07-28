package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.Model.Discount;
import grocerPanel.database.DiscountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class discountController {
    @FXML
    private TableView<Discount> discountTable;

    @FXML
    private TableColumn<Discount,String> codeColumn;

    @FXML
    private TableColumn<Discount,String> typeColumn;

    @FXML
    private TableColumn<Discount,Double> valueColumn;

    @FXML
    private TableColumn<Discount,String> expirationColumn;

    @FXML
    private TextField codeField;

    @FXML
    private ChoiceBox<String> typeBox;

    @FXML
    private TextField valueField;

    @FXML
    private TextField expirationField;

    private Discount selectedDiscount;

    @FXML
    private void onClose(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onAdd(ActionEvent event) throws IOException{

        String code = codeField.getText();
        String type = typeBox.getValue();
        String expiration = expirationField.getText();

        double value = Double.parseDouble(valueField.getText());

        if(DiscountDAO.addDiscount(code, type, value, expiration)) {
            refreshTable();
            clearFields();
        }
    }


    @FXML
    private void onUpdate(ActionEvent event) throws IOException{
        Discount selected = discountTable.getSelectionModel().getSelectedItem();

        if(selected == null){
            return;
        }
        double value = Double.parseDouble(valueField.getText());
        DiscountDAO.updateDiscount( selected.getDiscountID(), codeField.getText(), typeBox.getValue(), value, expirationField.getText());
        refreshTable();
        clearFields();

    }


    @FXML
    private void onDelete(ActionEvent event) throws IOException{
        Discount selected = discountTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            return;
        }
        DiscountDAO.deleteDiscount(selected.getDiscountID());
        refreshTable();
        clearFields();

    }

    private void refreshTable(){
        discountTable.setItems(DiscountDAO.getAllDiscounts());
    }

    private void clearFields(){
        codeField.clear();
        valueField.clear();
        expirationField.clear();
        typeBox.setValue(null);
    }


    @FXML
    public void initialize(){

        typeBox.getItems().addAll(
            "Percent",
            "Fixed"
        );

        codeColumn.setCellValueFactory(
            new PropertyValueFactory<>("code")
        );

        typeColumn.setCellValueFactory(
            new PropertyValueFactory<>("discountType")
        );

        valueColumn.setCellValueFactory(
            new PropertyValueFactory<>("discountValue")
        );

        expirationColumn.setCellValueFactory(
            new PropertyValueFactory<>("expirationDate")
        );


        discountTable.setItems(DiscountDAO.getAllDiscounts());
    }

}
