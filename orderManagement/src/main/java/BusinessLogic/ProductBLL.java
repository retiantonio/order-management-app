package BusinessLogic;

import DataAccess.ClientDAO;
import DataAccess.ProductDAO;
import DataModel.Client;
import DataModel.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing operations related to Products.
 * This class acts as an intermediary between the data access layer (ProductDAO)
 * and the rest of the application, providing methods to find, retrieve, update,
 * and insert product data.
 *
 * <p>It performs validation to ensure products exist before returning them,
 * throwing exceptions if products are not found.
 *
 * <p>Example usage:
 * <pre>
 * ProductBLL productBLL = new ProductBLL();
 * Product product = productBLL.findProductByID(10);
 * List&lt;Product&gt; allProducts = productBLL.findAllProducts();
 * productBLL.updateProduct(product);
 * productBLL.insertProduct(new Product(...));
 * </pre>
 */
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

    public void deleteProduct(Product product) {
        if(productDAO.delete((int)product.getId())) {
            System.out.println("The product was successfully deleted");
        } else {
            System.out.println("The product was not successfully deleted");
        }
    }
}
