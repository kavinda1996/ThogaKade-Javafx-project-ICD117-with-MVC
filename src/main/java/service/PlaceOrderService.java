package service;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Customer;
import model.dto.Item;
import model.dto.Orders;

import java.sql.SQLException;

public interface PlaceOrderService {
    Customer getCustomer(String customerId);

    Item getItem(String itemCode);

    void plceOrder(Orders orders, ObservableList<CartItem> cartItemObservableList) throws SQLException;
}
