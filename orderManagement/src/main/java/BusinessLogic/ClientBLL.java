package BusinessLogic;

import DataAccess.ClientDAO;
import DataModel.Client;
import DataModel.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing operations related to Clients.
 * This class acts as an intermediary between the data access layer (ClientDAO)
 * and the rest of the application, providing methods to find, insert, and update clients.
 *
 * <p>It includes validation to handle cases where clients are not found.
 *
 * <p>Example usage:
 * <pre>
 * ClientBLL clientBLL = new ClientBLL();
 * Client client = clientBLL.findClientByID(1);
 * List&lt;Client&gt; clients = clientBLL.findAllClients();
 * clientBLL.insertClient(new Client(...));
 * clientBLL.updateClient(existingClient);
 * </pre>
 */
public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public Client findClientByID(int id) {
        Client client = clientDAO.findById(id);

        if(client == null) {
            throw new NoSuchElementException("The client was not found");
        }

        return client;
    }

    public List<Client> findAllCLients() {
        List<Client> clients = clientDAO.findAll();

        if(clients == null) {
            throw new NoSuchElementException("The clients were not found");
        }

        return clients;
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    public void insertClient(Client client) {
        clientDAO.insert(client);
    }

    public void deleteClient(Client client) {
        if(clientDAO.delete((int)client.getId())) {
            System.out.println("The product was successfully deleted");
        } else {
            System.out.println("The product was not successfully deleted");
        }
    }
}
