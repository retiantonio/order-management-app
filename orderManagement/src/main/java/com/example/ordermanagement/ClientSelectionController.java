package com.example.ordermanagement;

import DataModel.Client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller class for client selection UI.
 *
 * <p>This class manages a TableView that displays a list of clients,
 * allowing the user to select one client and confirm their choice.
 *
 * <p>The controller provides methods to populate the table with client data,
 * handle user confirmation, and retrieve the selected client.
 */
public class ClientSelectionController {

    @FXML private TableView<Client> clientSelectionTable;

    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> nameColumn;
    @FXML private TableColumn<Client, String> addressColumn;
    @FXML private TableColumn<Client, String> phoneColumn;

    private Client selectedClient;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData ->cellData.getValue().idClientProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameClientProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressClientProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneClientProperty());
    }

    @FXML
    private void confirmClient(ActionEvent event) {
        selectedClient = clientSelectionTable.getSelectionModel().getSelectedItem();

        if(selectedClient != null) {
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }
    }

    public void setClientList(ObservableList<Client> clients) {
        clientSelectionTable.setItems(clients);
    }

    public Client getSelectedClient() {
        return selectedClient;
    }
}
