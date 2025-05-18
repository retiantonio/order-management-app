package BusinessLogic;

import DataAccess.ClientDAO;
import DataModel.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findSClientByID(int id) {
        Client client = clientDAO.findById(id);

        if(client == null) {
            throw new NoSuchElementException("The client was not found");
        }

        return client;
    }

    public List<Client> findAllCLient() {
        List<Client> clients = clientDAO.findAll();

        if(clients == null) {
            throw new NoSuchElementException("The clients were not found");
        }

        return clients;
    }
}
