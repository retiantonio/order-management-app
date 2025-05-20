package com.example.ordermanagement;

import DataModel.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Controller for the product order UI component.
 *
 * <p>Manages the display of product details, including name, price, and stock,
 * and allows users to select the quantity to order via a spinner control.</p>
 *
 * <p>Updates the total price dynamically when the quantity is changed.</p>
 */
public class ProductOrderController {

    private Product productObject;

    private double totalPrice = 0.0;

    @FXML private Label productOrderNameLabel;
    @FXML private Label productOrderPriceLabel;
    @FXML private Label productOrderStockLabel;

    @FXML private Spinner<Integer> productOrderStockSpinner;

    /**
     * Restores the UI fields with the given product's data.
     * Initializes the quantity spinner with a range from 1 to the product's stock.
     * Sets up a listener to update the total price label when the quantity changes.
     *
     * @param product the Product object to display in the UI
     */
    public void restoreUI(Product product) {
        productObject = product;

        productOrderNameLabel.setText(productObject.getName());
        productOrderPriceLabel.setText(productObject.getPrice() + " lei");
        productOrderStockLabel.setText("Pieces in stock: " + productObject.getStock());

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, productObject.getStock());

        valueFactory.setValue(1);
        productOrderStockSpinner.setValueFactory(valueFactory);

        totalPrice = productObject.getPrice() * productOrderStockSpinner.getValue();

        productOrderStockSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                totalPrice = productObject.getPrice() * productOrderStockSpinner.getValue();
                productOrderPriceLabel.setText(totalPrice + " lei");
            }
        });
    }

    public int getProductOrderQuantity() {
        return productOrderStockSpinner.getValue();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Product getProductObject() {
        return productObject;
    }
}
