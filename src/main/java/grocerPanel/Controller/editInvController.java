package grocerPanel.Controller;

import java.io.IOException;

import grocerPanel.Model.Product;
import grocerPanel.Model.User;
import grocerPanel.database.ProductDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
public class editInvController {

    @FXML
    private Button CancelButton;

    @FXML
    private TextField CodeField;

    @FXML
    private TextArea DescriptionArea;

    @FXML
    private TextField DiscountField;

    @FXML
    private TextField IDfield;

    @FXML
    private TextField NameField;

    @FXML
    private ImageView PictureView;

    @FXML
    private TextField PriceField;

    @FXML
    private TextField QuantityField;

    @FXML
    private Button SaveButton;
    private Product currentProduct;
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setProduct(Product product) {
        this.currentProduct = product;

        IDfield.setText(String.valueOf(product.getProductID()));
        IDfield.setDisable(true); // ID is a primary key, shouldn't be editable

        NameField.setText(product.getName());
        DescriptionArea.setText(product.getDescription());
        PriceField.setText(String.valueOf(product.getPrice()));
        QuantityField.setText(String.valueOf(product.getQuantity()));
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        // No changes are applied to currentProduct here, so nothing to undo
        returnToMainPage(event);
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        if (currentProduct == null) {
            return;
        }

        String name = NameField.getText().trim();
        String description = DescriptionArea.getText().trim();
        String priceText = PriceField.getText().trim();
        String quantityText = QuantityField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            showAlert("Missing Info", "Please fill in name, price, and quantity.");
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Price must be a number and quantity must be a whole number.");
            return;
        }

        currentProduct.setName(name);
        currentProduct.setDescription(description);
        currentProduct.setPrice(price);
        currentProduct.setQuantity(quantity);

        ProductDAO.updateProduct(currentProduct);

        returnToMainPage(event);
    }

    private void returnToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grocerPanel/main-page.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());

        mainController controller = loader.getController();
        controller.setCurrentUser(currentUser);

        stage.setScene(scene);
        stage.setTitle("GroccerPanel - Main Page");
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
