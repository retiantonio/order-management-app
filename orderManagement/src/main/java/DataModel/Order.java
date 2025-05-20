package DataModel;

import java.util.Date;

/**
 * Represents an order placed by a client for a specific product.
 * Contains details such as order ID, product ID, client ID, date, quantity, and total price.
 */
public class Order {

    private long id;
    private Date date;
    private long idProduct;
    private long idClient;

    private int quantity;
    private double price;

    /**
     * Default constructor.
     * Initializes an empty Order object.
     */
    public Order() {}

    /**
     * Constructs an Order with the specified details.
     *
     * @param date      the date of the order
     * @param idProduct the ID of the product
     * @param idClient  the ID of the client
     * @param quantity  the quantity of the product
     * @param price     the total price of the order
     */
    public Order(Date date, long idProduct, long idClient, int quantity, double price) {
        this.date = date;

        this.idProduct = idProduct;
        this.idClient = idClient;

        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }
}
