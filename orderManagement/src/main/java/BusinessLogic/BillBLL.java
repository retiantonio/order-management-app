package BusinessLogic;

import DataAccess.BillDAO;
import DataModel.Bill;
import DataModel.Order;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class responsible for handling operations related to Bills.
 * It acts as an intermediary between the Data Access Layer (BillDAO) and the application logic,
 * providing methods to retrieve and insert Bill records with necessary validations.
 *
 * <p>This class manages the lifecycle and business rules related to {@link Bill} objects.
 *
 * <p>Example usage:
 * <pre>
 * BillBLL billBLL = new BillBLL();
 * List&lt;Bill&gt; bills = billBLL.getAllBills();
 * billBLL.insertBill(new Bill(...));
 * </pre>
 */
public class BillBLL {

    private BillDAO billDAO;

    public BillBLL() {
        billDAO = new BillDAO();
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = billDAO.findAll();

        if(bills == null) {
            throw new NoSuchElementException("The bills were not found");
        }

        return bills;
    }

    public void insertBill(Bill bill) {
        billDAO.insert(bill);
    }
}
