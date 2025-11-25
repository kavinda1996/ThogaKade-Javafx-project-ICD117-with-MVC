package service;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Orders;

public interface OrderDetailsService {

    boolean addOrderDetails(Orders orders, ObservableList<CartItem> cartItems);
}
