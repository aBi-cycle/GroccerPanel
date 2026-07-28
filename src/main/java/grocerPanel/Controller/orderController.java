package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.Model.Order;
import grocerPanel.Model.User;
import grocerPanel.database.OrderDAO;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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

@SuppressWarnings("unused")
public class orderController {

    
    @FXML
    private Button AddB;

    @FXML
    private Button EditB;

    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private Button ProductB;

    @FXML
    private Button discountButton;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> dateColumn;

    @FXML
    private TableColumn<Order, String> nameColumn;

    @FXML
    private TableColumn<Order, Double> amountColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    private FilteredList<Order> filteredOrders;

    @FXML
    void onSearch(ActionEvent event) {

        String searchText = searchBox.getText().toLowerCase();

        filteredOrders.setPredicate(order -> {

            if(searchText.isEmpty()) {
                return true;
            }

            return order.getCustomerName().toLowerCase().contains(searchText)
                || order.getStatus().toLowerCase().contains(searchText)
                || order.getOrderDate().toLowerCase().contains(searchText)
                || String.valueOf(order.getOrderID()).contains(searchText);
        });
    }

    @FXML
    void onProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/main-page.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        mainController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Main Page");
        stage.show();
    }

    @FXML
    void onDiscount(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/discount-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Discount Manager");
        stage.show();
    }

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;

        boolean isManager = "Manager".equalsIgnoreCase(user.getRole());

        discountButton.setVisible(isManager);
        discountButton.setDisable(!isManager);
        dateColumn.setEditable(isManager);
        nameColumn.setEditable(isManager);
        amountColumn.setEditable(isManager);
    }

     @FXML
    public void initialize() {
        orderTable.setEditable(true);
        

        idColumn.setCellValueFactory(
            new PropertyValueFactory<>("orderID")
        );
        dateColumn.setCellValueFactory(
            new PropertyValueFactory<>("orderDate")
        );
        nameColumn.setCellValueFactory(
            new PropertyValueFactory<>("customerName")
        );
        amountColumn.setCellValueFactory(
            new PropertyValueFactory<>("totalAmount")
        );
        statusColumn.setCellValueFactory(
            new PropertyValueFactory<>("status")
        );

        filteredOrders = new FilteredList<>(OrderDAO.getAllOrders(), o -> true);

        orderTable.setItems(filteredOrders);

        orderTable.sort();

        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        dateColumn.setOnEditCommit(event -> {
            Order order = event.getRowValue();
            order.setOrderDate(event.getNewValue());
            OrderDAO.updateOrder(order);
            orderTable.refresh(); // Refresh the table to show updated value
        });

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setOnEditCommit(event -> {
            Order order = event.getRowValue();
            order.setCustomerName(event.getNewValue());
            OrderDAO.updateOrder(order);
            orderTable.refresh(); // Refresh the table to show updated value
        });

        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        amountColumn.setOnEditCommit(event -> {
            Order order = event.getRowValue();
            order.setTotalAmount(event.getNewValue());
            OrderDAO.updateOrder(order);
            orderTable.refresh(); // Refresh the table to show updated value
        });

        statusColumn.setEditable(true);

        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
            FXCollections.observableArrayList(
                    "In Progress",
                    "Completed",
                    "Cancelled"
                )
            )
        );

        statusColumn.setOnEditCommit(event -> {
            Order order = event.getRowValue();
            order.setStatus(event.getNewValue());
            OrderDAO.updateOrder(order);
            orderTable.refresh();
        });

        
    }

    @FXML
    void onAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/add-order.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        addOrderController controller = loader.getController();
        controller.setCurrentUser(currentUser);

        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Add Order");
        stage.show();
    }

    @FXML
    void onEdit(ActionEvent event) throws IOException {
        Order selected = orderTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Select an order to edit.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/edit-order.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        editOrderController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        controller.setOrder(selected);

        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Edit Order");
        stage.show();
    }

}
