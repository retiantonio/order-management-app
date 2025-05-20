package DataModel;

/**
 * Represents a product with relevant details such as name, availability,
 * stock level, price, and customer rating.
 */
public class Product {

    private String name;

    private boolean inStore;

    private long id;

    private int stock;

    private double rating;
    private double price;

    /**
     * Default constructor.
     * Initializes an empty Product object.
     */
    public Product() {}

    /**
     * Constructs a Product with the specified details.
     *
     * @param name    the name of the product
     * @param inStore whether the product is available in store
     * @param stock   the quantity in stock
     * @param price   the price of the product
     * @param rating  the customer rating of the product
     */
    public Product(String name, boolean inStore, int stock, double price, double rating) {
        this.name = name;
        this.inStore = inStore;

        this.stock = stock;
        this.price = price;

        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public boolean isInStore() {
        return inStore;
    }

    public int getStock() {
        return stock;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInStore(boolean inStore) {
        this.inStore = inStore;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }
}
