package controller.ordersController;

import javafx.collections.ObservableList;
import model.Orders;

public interface OrdersManagementService {

    public void addOrders(Orders orders);

    public void deleteOrders (String orderID);

    public void updateOrders (Orders orders);

    ObservableList<Orders> getAllOrders();
}
