package BusinessLogic;

import DataAccess.OrderDAO;
import DataModel.Order;
import DataModel.Product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing operations related to Orders.
 * This class acts as a mediator between the data access layer (OrderDAO)
 * and the rest of the application, providing methods to find, retrieve, and insert orders.
 *
 * <p>It performs checks to ensure orders exist before returning them and throws exceptions otherwise.
 *
 * <p>Example usage:
 * <pre>
 * OrderBLL orderBLL = new OrderBLL();
 * Order order = orderBLL.findOrderByID(5);
 * List&lt;Order&gt; allOrders = orderBLL.getAllOrders();
 * orderBLL.insertOrder(new Order(...));
 * </pre>
 */
public class OrderBLL {

    private OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public Order findOrderByID(int id) {
        Order order = orderDAO.findById(id);

        if(order == null) {
            throw new NoSuchElementException("The order was not found");
        }

        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.findAll();

        if(orders == null) {
            throw new NoSuchElementException("The orders were not found");
        }

        return orders;
    }

    public void insertOrder(Order order) {
        orderDAO.insert(order);
    }
}
