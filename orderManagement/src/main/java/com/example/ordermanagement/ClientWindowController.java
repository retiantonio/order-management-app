package com.example.ordermanagement;

import BusinessLogic.ClientBLL;
import DataModel.Client;
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
 * Controller for the client management window.
 *
 * <p>This controller handles displaying a list of clients in a FlowPane,
 * supports adding new clients dynamically, and switching to the product page.
 *
 * <p>Clients are loaded from the business logic layer on initialization.
 * UI components for each client are created reflectively using UIFactory helper.
 */
public class ClientWindowController {

    private ClientBLL clientBLL;

    List<Client> clients = new ArrayList<>();

    @FXML private FlowPane clientWindowFlowPane;

    @FXML
    private void initialize() {
        clientBLL = new ClientBLL();
        clients = clientBLL.findAllCLients();

        UIFactory.callRestoreUIReflectively(this, clients);
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

    @FXML
    private void addNewClient() {
        createClientComponent(null);
    }


    /**
     * Function called by the reflective method to restore every component.
     *
     * @param clients
     */
    private void restoreUI(List<Client> clients) {
        for (Client client : clients) {
            createClientComponent(client);
        }
    }

    /**
     * Creates a client UI component from the FXML and adds it to the FlowPane.
     * If a client is provided, it restores the client's data into the UI.
     *
     * @param client The client data to restore in the UI component; null for new empty client.
     */
    private void createClientComponent(Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client-view.fxml"));
            Node component = loader.load();

            ClientController controller = loader.getController();

            if(client != null) {
                controller.restoreClientUI(client);
            }

            clientWindowFlowPane.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
