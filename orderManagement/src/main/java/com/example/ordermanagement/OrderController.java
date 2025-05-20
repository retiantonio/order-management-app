package com.example.ordermanagement;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import DataModel.Bill;
import DataModel.Client;
import DataModel.Order;
import DataModel.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class responsible for displaying order details in the UI.
 *
 * <p>It retrieves and shows order information, including associated product and client details,
 * by interacting with the business logic layer.
 */
public class OrderController {

    @FXML private Label orderIDLabel;
    @FXML private Label orderProductLabel;
    @FXML private Label orderClientLabel;
    @FXML private Label orderPriceLabel;
    @FXML private Label orderQuantityLabel;
    @FXML private  Label orderAddressLabel;
    @FXML private Label orderDateLabel;

    public void restoreOrderUI(Order order) {
        orderIDLabel.setText(String.valueOf(order.getId()));

        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductByID((int)order.getIdProduct());
        orderProductLabel.setText(String.valueOf(product.getName()));

        ClientBLL clientBLL = new ClientBLL();
        Client client = clientBLL.findClientByID((int)order.getIdClient());
        orderClientLabel.setText(client.getName());

        orderPriceLabel.setText(String.valueOf(order.getPrice()));

        orderQuantityLabel.setText(String.valueOf(order.getQuantity()));
        orderAddressLabel.setText(client.getAddress());
        orderDateLabel.setText(String.valueOf(order.getDate()));
    }
}
