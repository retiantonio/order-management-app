package com.example.ordermanagement;

import BusinessLogic.OrderBLL;
import DataModel.Order;
import HelpMethods.UIFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for displaying and managing the order window.
 *
 * <p>This class loads all existing orders from the business logic layer
 * and dynamically creates UI components for each order in a FlowPane.</p>
 *
 * <p>It also provides navigation back to the main shop window.</p>
 */
public class OrderWindowController {

    private OrderBLL orderBLL;

    private List<Order> orders = new ArrayList<>();

    @FXML private FlowPane orderWindowFlowPane;

    @FXML
    private void initialize() {
       orderBLL = new OrderBLL();
       orders = orderBLL.getAllOrders();

        UIFactory.callRestoreUIReflectively(this, orders);
    }

    @FXML
    private void switchToMainWindow(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-shop-view.fxml"));

        try {
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restores the UI components for the list of orders.
     * Called by the reflective Method in UIFactory.
     * @param orders the list of orders to be displayed in the UI
     */
    private void restoreUI(List<Order> orders) {
        for (Order order : orders) {
            createOrderComponent(order);
        }
    }

    /**
     * Creates a UI component for a single order and adds it to the FlowPane.
     *
     * @param order the Order object to create the UI component for
     */
    private void createOrderComponent(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("order-view.fxml"));
            Node component = loader.load();

            OrderController controller = loader.getController();

            controller.restoreOrderUI(order);

            orderWindowFlowPane.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
