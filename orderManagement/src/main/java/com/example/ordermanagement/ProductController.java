package com.example.ordermanagement;

import BusinessLogic.ProductBLL;
import DataModel.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProductController {

    private boolean isAddedToShoppingCart = false;

    private Product productObject;
    private ProductBLL productBLL;

    @FXML private TextField productNameTF;
    @FXML private TextField productInStoreTF;
    @FXML private TextField productStockTF;
    @FXML private TextField productPriceTF;
    @FXML private TextField productRatingTF;


    @FXML
    private void initialize() {
        productBLL = new ProductBLL();
    }

    @FXML
    private void addToOrder(ActionEvent event ) {
        if(productObject.getStock() > 0) {
            isAddedToShoppingCart = true;
        } else {
            //do something
        }
    }

    @FXML
    private void saveProduct(ActionEvent event) {
        if(productObject != null) {
            productObject.setName(productNameTF.getText());

            if(productInStoreTF.getText().equals("In store")) {
                productObject.setInStore(true);
            } else {
                productObject.setInStore(false);
            }

            productObject.setStock(Integer.parseInt(productStockTF.getText()));
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
