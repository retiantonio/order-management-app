package com.example.ordermanagement;

import BusinessLogic.ProductBLL;
import DataModel.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 * Controller for managing the UI and logic of individual product components.
 *
 * <p>This controller handles displaying product details, modifying products,
 * adding products to the shopping cart, and saving product data through the business logic layer.</p>
 */
public class ProductController {

    private boolean isAddedToShoppingCart = false;
    private boolean toRemove = false;

    private Product productObject;
    private ProductBLL productBLL;


    @FXML private TextField productNameTF;
    @FXML private TextField productInStoreTF;
    @FXML private TextField productStockTF;
    @FXML private TextField productPriceTF;
    @FXML private TextField productRatingTF;

    @FXML private VBox productViewVB;

    @FXML private Button productOrderButton;
    @FXML private Button productModifyButton;


    @FXML
    private void initialize() {
        productBLL = new ProductBLL();
    }

    /**
     * Handles the action of adding the current product to the shopping cart.
     * The product must have stock available to be added.
     *
     * @param event the event triggered by clicking the add to order button
     */
    @FXML
    private void addToOrder(ActionEvent event ) {
        if(productObject != null) {
            if(productObject.getStock() > 0) {
                isAddedToShoppingCart = true;
            }
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        if(productObject != null) {
            productBLL.deleteProduct(productObject);
            toRemove = true;
        }
    }

    /**
     * Saves the current product details.
     * If the product already exists, it updates it; otherwise, it inserts a new product.
     *
     * @param event the event triggered by clicking the save button
     */
    @FXML
    private void saveProduct(ActionEvent event) {
        if(productObject != null) {
            productObject.setName(productNameTF.getText());

            if(productInStoreTF.getText().equals("In store")) {
                productObject.setInStore(true);
            } else {
                productObject.setInStore(false);
            }

            if(productStockTF.getText().equals("Not available")) {
                productObject.setStock(0);
            } else {
                productObject.setStock(Integer.parseInt(productStockTF.getText()));
                productOrderButton.setStyle("-fx-background-color: linear-gradient(to right, #ED6663, #f08482);");
            }

            productObject.setPrice(Double.parseDouble(productPriceTF.getText()));
            productObject.setRating(Double.parseDouble(productRatingTF.getText()));

            productBLL.updateProduct(productObject);
        } else {
            String productName = productNameTF.getText();

            boolean productInStore;
            if(productInStoreTF.getText().equals("In store")) {
                productInStore = true;
            } else {
                productInStore = false;
            }

            int productStock = Integer.parseInt(productStockTF.getText());

            double productPrice = Double.parseDouble(productPriceTF.getText());
            double productRating = Double.parseDouble(productRatingTF.getText());

            productObject = new Product(productName, productInStore, productStock, productPrice, productRating);
            productBLL.insertProduct(productObject);
        }
    }

    /**
     * Restores the UI fields with the data from the given product.
     * Also, "disables" (style) the order button and adjusts UI if the product is out of stock.
     *
     * @param product the Product object to restore the UI from
     */
    public void restoreProductUI(Product product) {
        productObject = product;

        productNameTF.setText(productObject.getName());

        if (productObject.isInStore()) {
            productInStoreTF.setText("In store");
        } else {
            productInStoreTF.setText("Not available in store");
        }

        productStockTF.setText(String.valueOf(productObject.getStock()));
        productPriceTF.setText(String.valueOf(productObject.getPrice()));
        productRatingTF.setText(String.valueOf(productObject.getRating()));

        if(productObject.getStock() == 0) {
            productOrderButton.setStyle("-fx-background-color: linear-gradient(to right, #746D6D, #B1B0B0);");

            productStockTF.setText("Not available");
            productInStoreTF.setText("Not available");
        }
    }

    public boolean isAddedToShoppingCart() {
        return isAddedToShoppingCart;
    }

    public void setAddedToShoppingCart(boolean status) {
        isAddedToShoppingCart = status;
    }

    public Product getProductObject() {
        return productObject;
    }
}
