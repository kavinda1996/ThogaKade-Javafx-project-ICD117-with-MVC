package service.impl;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.OrderDetail;
import model.dto.Orders;
import repository.OrderDetailRepository;
import repository.impl.OrderdetailsRepositoryImpl;
import service.OrderDetailsService;

import java.sql.SQLException;

public class OrderDetailsServiceImpl implements OrderDetailsService {

    OrderDetailRepository orderDetialsRepository = new OrderdetailsRepositoryImpl();
    @Override
    public boolean addOrderDetails(Orders orders, ObservableList<CartItem> cartItems) {

        boolean isAdd = false;

        for (CartItem cartItem:cartItems){
            try {
                isAdd = orderDetialsRepository.addOrderdetails(
                        new OrderDetail(
                                orders.getOrderId(),
                                cartItem.getItemCode(),
                                cartItem.getQuantity(),
                                cartItem.getDiscount()
                        )
                );
                if(isAdd == false) {
                    break;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isAdd;
    }
}
