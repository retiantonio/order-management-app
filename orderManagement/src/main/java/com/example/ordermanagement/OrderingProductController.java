package com.example.ordermanagement;

import BusinessLogic.BillBLL;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import DataModel.Bill;
import DataModel.Client;
import DataModel.Order;
import DataModel.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Controller class responsible for managing the ordering process of products.
 *
 * <p>This class handles the UI related to the shopping cart where products
 * can be added with quantities, calculates the price totals and delivery fee,
 * and allows checkout by creating Orders and Bills and updating product stocks.</p>
 *
 * <p>It also provides client selection functionality via a modal dialog.</p>
 */
public class OrderingProductController {

    private List<Product> products = new ArrayList<>();
    private List<ProductOrderController> productOrderControllers = new ArrayList<>();

    private Client selectedClient;

    private double productsPrice = 0.0;

    @FXML private VBox orderProductCartVBox;

    @FXML private Label orderingProductProductsLabel;
    @FXML private Label orderingProductDeliveryFeeLabel;
    @FXML private Label orderingProductTotalLabel;
    @FXML private Label orderingViewClientLabel;

    private ScheduledExecutorService updateScheduler = Executors.newScheduledThreadPool(1);

    @FXML
    private void checkout(ActionEvent event) {
        //create the order
        for(ProductOrderController productOrderController : productOrderControllers) {
            Product product = productOrderController.getProductObject();
            int quantity = productOrderController.getProductOrderQuantity();
            double price = product.getPrice() * quantity;

            if(selectedClient != null) {
                Order order = new Order(Date.valueOf(LocalDate.now()), product.getId(), selectedClient.getId(), quantity, price);
                OrderBLL orderBLL = new OrderBLL();
                orderBLL.insertOrder(order);

                product.setStock(product.getStock() - quantity);
                ProductBLL productBLL = new ProductBLL();
                productBLL.updateProduct(product);

                //insert new bill for this specific order
                Bill bill = new Bill(order.getId(), price, quantity, Date.valueOf(LocalDate.now()));
                BillBLL billBLL = new BillBLL();
                billBLL.insertBill(bill);
            }
        }
    }

    @FXML
    private void addClient(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client-selection-window.fxml"));

        try {
            Parent root = fxmlLoader.load();

            ClientSelectionController controller = fxmlLoader.getController();
            controller.setClientList(FXCollections.observableArrayList(new ClientBLL().findAllCLients()));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Client Selection");

            stage.setScene(new Scene(root));
            stage.showAndWait();

            if(controller.getSelectedClient() != null) {
                selectedClient = controller.getSelectedClient();
                orderingViewClientLabel.setText(selectedClient.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToProductPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-shop-view.fxml"));

        try {
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void restoreUI() {
        for (Product product : products) {
            createProductUI(product);
        }

        startUpdateThread();
    }

    /**
     * Starts a scheduled background task to periodically update
     * the total product price, delivery fee, and total price labels.
     */
    private void startUpdateThread() {
        updateScheduler.scheduleAtFixedRate(() -> {
            productsPrice = 0.0;
            for(ProductOrderController productOrderController : productOrderControllers) {
                productsPrice += productOrderController.getTotalPrice();
            }
            Platform.runLater(() -> {
                orderingProductProductsLabel.setText("Products: " + String.format("%.2f", productsPrice) + " lei");
                double deliveryPrice = productsPrice * 0.02;
                orderingProductDeliveryFeeLabel.setText("Delivery Fee: " + String.format("%.2f", deliveryPrice) + " lei");
                orderingProductTotalLabel.setText("Total: " + String.format("%.2f", productsPrice + deliveryPrice) + "lei");
            });
        }, 0, 250, TimeUnit.MILLISECONDS);
    }

    /**
     * Creates and adds a product UI component to the order cart VBox.
     *
     * @param product the Product to create the UI component for
     */
    private void createProductUI(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product-order-view.fxml"));
            Node component = loader.load();

            ProductOrderController controller = loader.getController();
            productOrderControllers.add(controller);

            if(product != null) {
                controller.restoreUI(product);
            }

            orderProductCartVBox.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
