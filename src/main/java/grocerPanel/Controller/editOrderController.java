package grocerPanel.Controller;

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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class editOrderController {

    @FXML private TextField IDfield;
    @FXML private TextField DateField;
    @FXML private TextField CustomerField;
    @FXML private TextField AmountField;
    @FXML private TextField StatusField;
    @FXML private Button ExecuteButton;
    @FXML private ListView<Product> itemListView;

    private User currentUser;
    private Order currentOrder;
    private final Map<Integer, Integer> selectedQuantities = new HashMap<>();

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setOrder(Order order) {
        this.currentOrder = order;

        IDfield.setText(String.valueOf(order.getOrderID()));
        IDfield.setDisable(true);

        DateField.setText(order.getOrderDate());
        CustomerField.setText(order.getCustomerName());
        AmountField.setText(String.format("%.2f", order.getTotalAmount()));

        StatusField.setText(order.getStatus());
        StatusField.setDisable(true);

        boolean alreadyCompleted = "Completed".equalsIgnoreCase(order.getStatus());
        ExecuteButton.setDisable(alreadyCompleted);

        for (OrderItems item : OrderDAO.getOrderItems(order.getOrderID())) {
            selectedQuantities.put(item.getProductID(), item.getQuantity());
        }

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

        updateTotal();
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
    void onSave(ActionEvent event) throws IOException {
        if (currentOrder == null) return;

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

        currentOrder.setOrderDate(date);
        currentOrder.setCustomerName(customer);
        currentOrder.setTotalAmount(Double.parseDouble(AmountField.getText()));

        OrderDAO.updateOrder(currentOrder);

        List<OrderItems> items = new ArrayList<>();
        for (Product product : itemListView.getItems()) {
            Integer qty = selectedQuantities.get(product.getProductID());
            if (qty != null) {
                items.add(new OrderItems(product.getProductID(), product.getName(), product.getPrice(), qty));
            }
        }
        OrderDAO.saveOrderItems(currentOrder.getOrderID(), items);

        returnToOrderPage(event);
    }

    @FXML
    void onDelete(ActionEvent event) throws IOException {
        if (currentOrder == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Order");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete order #" + currentOrder.getOrderID() + "? This cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (OrderDAO.deleteOrder(currentOrder.getOrderID())) {
                returnToOrderPage(event);
            } else {
                showAlert("Error", "Could not delete the order.");
            }
        }
    }

    @FXML
    void onExecute(ActionEvent event) throws IOException {
        if (currentOrder == null) return;

        if ("Completed".equalsIgnoreCase(currentOrder.getStatus())) {
            showAlert("Already Completed", "This order has already been executed.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Execute Order");
        confirm.setHeaderText(null);
        confirm.setContentText("Mark this order completed and remove these quantities from inventory?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            OrderDAO.executeOrder(currentOrder);
            returnToOrderPage(event);
        }
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