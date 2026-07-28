package grocerPanel.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grocerPanel.Model.Order;
import grocerPanel.Model.OrderItems;
import grocerPanel.Model.Product;
import grocerPanel.Model.User;
import grocerPanel.database.OrderDAO;
import grocerPanel.database.ProductDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class addOrderController {

    @FXML private TextField IDfield;
    @FXML private TextField DateField;
    @FXML private TextField CustomerField;
    @FXML private TextField AmountField;
    @FXML private TextField StatusField;
    @FXML private ListView<Product> itemListView;

    private User currentUser;
    private final Map<Integer, Integer> selectedQuantities = new HashMap<>();

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        IDfield.setText(String.valueOf(OrderDAO.getNextOrderID()));
        IDfield.setDisable(true);

        DateField.setText(LocalDate.now().toString());

        StatusField.setText("Pending");
        StatusField.setDisable(true);

        AmountField.setText("0.00");
        AmountField.setDisable(true);

        setupItemListView(ProductDAO.getAllProducts());
    }

    private void setupItemListView(ObservableList<Product> products) {
        itemListView.setItems(products);

        itemListView.setCellFactory(list -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            private final TextField qtyField = new TextField();
            private final HBox container = new HBox(10, checkBox, qtyField);

            {
                qtyField.setPrefWidth(50);
                qtyField.setDisable(true);

                checkBox.selectedProperty().addListener((obs, was, isSelected) -> {
                    Product product = getItem();
                    if (product == null) return;

                    qtyField.setDisable(!isSelected);

                    if (isSelected) {
                        selectedQuantities.putIfAbsent(product.getProductID(), 1);
                        qtyField.setText(String.valueOf(selectedQuantities.get(product.getProductID())));
                    } else {
                        selectedQuantities.remove(product.getProductID());
                    }
                    updateTotal();
                });

                qtyField.textProperty().addListener((obs, oldVal, newVal) -> {
                    Product product = getItem();
                    if (product == null || !checkBox.isSelected()) return;

                    try {
                        int qty = Math.max(1, Integer.parseInt(newVal.trim()));
                        selectedQuantities.put(product.getProductID(), qty);
                        updateTotal();
                    } catch (NumberFormatException ignored) {
                        // wait for a valid number as they finish typing
                    }
                });
            }

            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);

                if (empty || product == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                checkBox.setText(product.getName() + " ($" + product.getPrice() + ")");

                boolean isSelected = selectedQuantities.containsKey(product.getProductID());
                checkBox.setSelected(isSelected);
                qtyField.setDisable(!isSelected);
                qtyField.setText(String.valueOf(selectedQuantities.getOrDefault(product.getProductID(), 1)));

                setGraphic(container);
            }
        });
    }

    private void updateTotal() {
        double total = 0;
        for (Product product : itemListView.getItems()) {
            Integer qty = selectedQuantities.get(product.getProductID());
            if (qty != null) {
                total += product.getPrice() * qty;
            }
        }
        AmountField.setText(String.format("%.2f", total));
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        returnToOrderPage(event);
    }

    @FXML
    void onAdd(ActionEvent event) throws IOException {
        String date = DateField.getText().trim();
        String customer = CustomerField.getText().trim();

        if (date.isEmpty() || customer.isEmpty()) {
            showAlert("Missing Info", "Please fill in date and customer name.");
            return;
        }

        if (selectedQuantities.isEmpty()) {
            showAlert("No Items", "Select at least one item for this order.");
            return;
        }

        int id = Integer.parseInt(IDfield.getText());
        double amount = Double.parseDouble(AmountField.getText());

        Order newOrder = new Order(id, date, customer, amount, "In Progress");

        if (!OrderDAO.addOrder(newOrder)) {
            showAlert("Error", "Could not create the order.");
            return;
        }

        List<OrderItems> items = new ArrayList<>();
        for (Product product : itemListView.getItems()) {
            Integer qty = selectedQuantities.get(product.getProductID());
            if (qty != null) {
                items.add(new OrderItems(product.getProductID(), product.getName(), product.getPrice(), qty));
            }
        }
        OrderDAO.saveOrderItems(id, items);

        returnToOrderPage(event);
    }

    private void returnToOrderPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/order-page.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        orderController controller = loader.getController();
        controller.setCurrentUser(currentUser);

        stage.setScene(scene);
        stage.setTitle("GrocerPanel - Orders");
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}