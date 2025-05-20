package DataModel;

import java.util.Date;

/**
 * Represents a bill associated with a specific order.
 * A bill contains details such as the order ID, total price, quantity of items,
 * and the date the bill was issued.
 *
 * <p>This class is implemented as a Java record, which provides a compact syntax
 * for immutable data carriers.</p>
 *
 * @param orderId  the ID of the order this bill corresponds to
 * @param price    the total price of the bill
 * @param quantity the number of items included in the bill
 * @param date     the date the bill was created or issued
 */
public record Bill(long orderId, double price, int quantity, Date date) {

    @Override
    public long orderId() {
        return orderId;
    }

    @Override
    public double price() {
        return price;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public Date date() {
        return date;
    }
}
