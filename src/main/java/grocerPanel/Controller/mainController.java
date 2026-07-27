package grocerPanel.Controller;

import grocerPanel.Model.Product;
import grocerPanel.database.ProductDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    private TableColumn<Product, String> descriptionColumn;

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
        productTable.setEditable(true);
        

        idColumn.setCellValueFactory(
            new PropertyValueFactory<>("productID")
        );
        nameColumn.setCellValueFactory(
            new PropertyValueFactory<>("name")
        );
        descriptionColumn.setCellValueFactory(
            new PropertyValueFactory<>("description")
        );
        priceColumn.setCellValueFactory(
            new PropertyValueFactory<>("price")
        );
        quantityColumn.setCellValueFactory(
            new PropertyValueFactory<>("quantity")
        );

        productTable.setItems(ProductDAO.getAllProducts());

        productTable.getSortOrder().add(nameColumn);
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);

        productTable.sort();

        // Enables double-click editing for name column
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setName(event.getNewValue());
            ProductDAO.updateProduct(product);
            productTable.refresh(); // Refresh the table to show updated value
        });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setDescription(event.getNewValue());
            ProductDAO.updateProduct(product);
            productTable.refresh(); // Refresh the table to show updated value
        });

        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        priceColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setPrice(event.getNewValue());
            ProductDAO.updateProduct(product);
            productTable.refresh(); // Refresh the table to show updated value
        });

        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setQuantity(event.getNewValue());
            ProductDAO.updateProduct(product);
            productTable.refresh(); // Refresh the table to show updated value

        });

        
    }



}
