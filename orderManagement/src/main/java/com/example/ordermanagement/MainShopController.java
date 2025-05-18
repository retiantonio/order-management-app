package com.example.ordermanagement;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import DataModel.Client;
import DataModel.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();

        clients = clientBLL.findAllCLient();
        products = productBLL.findAllProducts();

        restoreProductUI();

        startUpdateThread();
    }

    @FXML
    private void addNewProduct() {
        createProductComponent(null);
    }

    private void startUpdateThread() {
        updateScheduler.scheduleAtFixedRate(() -> {
            for(ProductController controller : productControllers) {
                if(controller.isAddedToShoppingCart()) {
                    addedToShoppingCartProducts.add(controller.getProductObject());
                    controller.setAddedToShoppingCart(false);
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void restoreProductUI() {
        for(Product product : products) {
            createProductComponent(product);
        }
    }

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
