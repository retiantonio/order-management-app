package com.example.ordermanagement;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import DataModel.Client;
import DataModel.Product;
import HelpMethods.UIFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Controller for the main shop view.
 *
 * <p>Manages the display and interaction of products within the shop.
 * Handles adding new products, switching between different views
 * (shopping cart, client window, order window), and keeps track of products
 * added to the shopping cart.
 *
 * <p>Uses a background scheduled thread to periodically update the list
 * of products that have been added to the shopping cart by monitoring
 * individual ProductController instances.
 */
public class MainShopController {

    private ClientBLL clientBLL;
    private ProductBLL productBLL;

    private List<Client> clients;
    private List<Product> products;
    private List<Product> addedToShoppingCartProducts = new ArrayList<>();

    private ScheduledExecutorService updateScheduler = Executors.newScheduledThreadPool(1);

    private List<ProductController> productControllers = new ArrayList<>();

    @FXML private FlowPane mainShopFlowPane;

    @FXML
    private void initialize() {
        productBLL = new ProductBLL();
        products = productBLL.findAllProducts();

        //restoreUI();

        UIFactory.callRestoreUIReflectively(this, products);

        startUpdateThread();
    }

    @FXML
    private void addNewProduct() {
        createProductComponent(null);
    }

    @FXML
    private void switchToShoppingCart(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ordering-product-view.fxml"));

        try {
            Parent root = loader.load();

            OrderingProductController controller = loader.getController();
            controller.setProducts(addedToShoppingCartProducts);
            controller.restoreUI();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToClientWindow(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client-window-view.fxml"));

        try {
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToOrderWindow(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-window-view.fxml"));

        try {
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a scheduled task running every 500 milliseconds to check
     * which products have been added to the shopping cart.
     * Updates the internal list and resets flags in product controllers accordingly.
     */
    private void startUpdateThread() {
        updateScheduler.scheduleAtFixedRate(() -> {
//            for(ProductController controller : productControllers) {
//                if(controller.isAddedToShoppingCart()) {
//                    addedToShoppingCartProducts.add(controller.getProductObject());
//                    controller.setAddedToShoppingCart(false);
//                }
//            }

            productControllers.stream()
                    .filter(ProductController::isAddedToShoppingCart)
                    .peek(controller -> controller.setAddedToShoppingCart(false))
                    .map(ProductController::getProductObject)
                    .forEach(addedToShoppingCartProducts::add);
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void restoreUI(List<Product> products) {
        for(Product product : products) {
            createProductComponent(product);
        }
    }

    /**
     * Loads the product-view FXML, retrieves its controller, adds the controller
     * to the tracking list, restores the product data (if provided),
     * and adds the component to the main shop FlowPane.
     *
     * @param product The product to be displayed, or null to create an empty component.
     */
    private void createProductComponent(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-view.fxml"));
            Node component = loader.load();

            ProductController controller = loader.getController();
            productControllers.add(controller);

            if(product != null) {
                controller.restoreProductUI(product);
            }

            mainShopFlowPane.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
