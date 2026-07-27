package grocerPanel.Controller;

import grocerPanel.Model.Product;
import grocerPanel.database.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class mainController {

    @FXML
    private Button AddB;

    @FXML
    private Button EditB;

    @FXML
    private Button SearchB;

    @FXML
    private TextField SearchBox;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    void onSearch() {
        String searchTerm = SearchBox.getText().trim();
        // Implement search functionality here
    }

    @FXML
public void initialize() {

    idColumn.setCellValueFactory(
        new PropertyValueFactory<>("productID")
    );

    nameColumn.setCellValueFactory(
        new PropertyValueFactory<>("name")
    );

    priceColumn.setCellValueFactory(
        new PropertyValueFactory<>("price")
    );

    quantityColumn.setCellValueFactory(
        new PropertyValueFactory<>("quantity")
    );


    productTable.setItems(
        ProductDAO.getAllProducts()
    );
}



}
