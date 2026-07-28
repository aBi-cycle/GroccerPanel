package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.Model.Product;
import grocerPanel.Model.User;
import grocerPanel.database.ProductDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
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
    private Button OrderB;

    @FXML
    private Button managerButton;

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

   @FXML
    void onOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/order-page.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        orderController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Orders");
        stage.show();
    }

    @FXML
    void onSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/manager-view.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Manger Settings");
        stage.show();
    }

    private User currentUser;


    public void setCurrentUser(User user) {
        this.currentUser = user;

        boolean isManager = "Manager".equalsIgnoreCase(user.getRole());

        managerButton.setVisible(isManager);
        managerButton.setDisable(!isManager);
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
