package com.example.ordermanagement;

import BusinessLogic.ClientBLL;
import DataModel.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for managing client data in the JavaFX UI.
 *
 * <p>This class handles user input for creating or updating a client.
 * It connects UI fields to the business logic layer (ClientBLL) for database operations.
 *
 * <p>Use the {@code saveClient} method to persist changes or new client data,
 * and {@code restoreClientUI} to populate the UI with an existing client's information.
 */
public class ClientController {

    private Client clientObject;

    private ClientBLL clientBll = new ClientBLL();

    @FXML private TextField clientNameTF;
    @FXML private TextField clientAddressTF;
    @FXML private TextField clientPhoneTF;

    @FXML
    private void saveClient(ActionEvent event) {
        if(clientObject != null) {
            clientObject.setName(clientNameTF.getText());
            clientObject.setAddress(clientAddressTF.getText());
            clientObject.setPhone(clientPhoneTF.getText()); //need validator

            clientBll.updateClient(clientObject);
        } else {
            String clientName = clientNameTF.getText();
            String clientAddress = clientAddressTF.getText();
            String clientPhone = clientPhoneTF.getText();

            clientObject = new Client(clientName, clientAddress, clientPhone);

            clientBll.insertClient(clientObject);
        }
    }

    @FXML
    private void deleteClient(ActionEvent event) {
        if(clientObject != null) {
            clientBll.deleteClient(clientObject);
        }
    }

    public void restoreClientUI(Client client) {
        clientObject = client;

        clientNameTF.setText(clientObject.getName());
        clientAddressTF.setText(clientObject.getAddress());
        clientPhoneTF.setText(clientObject.getPhone());
    }
}
