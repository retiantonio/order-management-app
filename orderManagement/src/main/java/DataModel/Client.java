package DataModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a client with basic personal and contact information.
 * This model is used to store client data and provide JavaFX properties
 * for UI binding.
 */

public class Client {

    private long id;

    private String name;

    private String address;
    private String phone;

    /**
     * Default constructor.
     * Initializes an empty Client object.
     */
    public Client() {}

    /**
     * Constructs a Client with the specified name, address, and phone number.
     *
     * @param name    the name of the client
     * @param address the address of the client
     * @param phone   the phone number of the client
     */
    public Client(String name, String address, String phone) {
        this.name = name;

        this.address = address;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the JavaFX IntegerProperty for the client's ID.
     * Useful for data binding in JavaFX applications.
     *
     * @return the ID property
     */
    public IntegerProperty idClientProperty() {
        IntegerProperty idEmployeeProperty = new SimpleIntegerProperty((int)id);
        return idEmployeeProperty;
    }

    /**
     * Returns the JavaFX StringProperty for the client's name.
     * Useful for data binding in JavaFX applications.
     *
     * @return the name property
     */
    public StringProperty nameClientProperty() {
        SimpleStringProperty nameEmployeeProperty = new SimpleStringProperty(name);
        return nameEmployeeProperty;
    }

    /**
     * Returns the JavaFX StringProperty for the client's address.
     * Useful for data binding in JavaFX applications.
     *
     * @return the address property
     */
    public StringProperty addressClientProperty() {
        SimpleStringProperty addressEmployeeProperty = new SimpleStringProperty(address);
        return addressEmployeeProperty;
    }

    /**
     * Returns the JavaFX StringProperty for the client's phone number.
     * Useful for data binding in JavaFX applications.
     *
     * @return the phone property
     */
    public StringProperty phoneClientProperty() {
        SimpleStringProperty phoneEmployeeProperty = new SimpleStringProperty(phone);
        return phoneEmployeeProperty;
    }

}
