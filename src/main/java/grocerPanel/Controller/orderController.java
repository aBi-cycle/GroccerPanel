package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.Model.Order;
import grocerPanel.Model.User;
import grocerPanel.database.OrderDAO;
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

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;

        boolean canEdit = !"Employee".equalsIgnoreCase(user.getRole());

        // AddB.setDisable(!canEdit);
        // EditB.setDisable(!canEdit);
        // orderTable.setEditable(canEdit);
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

        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        statusColumn.setOnEditCommit(event -> {
            Order order = event.getRowValue();
            order.setStatus(event.getNewValue());
            OrderDAO.updateOrder(order);
            orderTable.refresh(); // Refresh the table to show updated value
        });

        
    }

}
