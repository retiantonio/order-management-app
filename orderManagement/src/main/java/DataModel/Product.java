package DataModel;

public class Product {


    private String name;

    private boolean inStore;

    private long id;

    private int stock;

    private double rating;
    private double price;

    public Product() {}

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
