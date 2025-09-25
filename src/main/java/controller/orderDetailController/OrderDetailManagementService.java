package controller.orderDetailController;

import javafx.collections.ObservableList;
import model.OrderDetail;

public interface OrderDetailManagementService {

    public void addOrderDetail(OrderDetail orderDetail);

    public void deleteOrderDetail(String orderID, String itemCode);

    public void updateOrderDetail(OrderDetail orderDetail);

    public ObservableList<OrderDetail> getAllOrderDetail();
}
