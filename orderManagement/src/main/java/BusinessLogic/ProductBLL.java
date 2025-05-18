package BusinessLogic;

import DataAccess.ClientDAO;
import DataAccess.ProductDAO;
import DataModel.Client;
import DataModel.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Product findProductByID(int id) {
        Product product = productDAO.findById(id);

        if(product == null) {
            throw new NoSuchElementException("The product was not found");
        }

        return product;
    }

    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();

        if(products == null) {
            throw new NoSuchElementException("The products were not found");
        }

        return products;
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void insertProduct(Product product) {
        productDAO.insert(product);
    }
}
