package service.impl;

import db.DBConnection;
import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Customer;
import model.dto.Item;
import model.dto.Orders;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderServiceImpl implements PlaceOrderService {

    ItemService itemService = new ItemServiceImpl();
    CustomerService customerService = new CustomerServiceImpl();

    @Override
    public Customer getCustomer(String customerId) {
        return customerService.getCustomer(customerId);
    }

    @Override
    public Item getItem(String itemCode) {
        return itemService.searchItem(itemCode, null);
    }

    OrderService orderService = new OrderServiceImpl();
    OrderDetailsService orderDetailsService = new OrderDetailsServiceImpl();


    @Override
    public void plceOrder(Orders orders, ObservableList<CartItem> cartItemObservableList) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isAddOrder = orderService.addOrder(orders);
            if(isAddOrder){
                boolean isAddOrderDeatils = orderDetailsService.addOrderDetails(orders, cartItemObservableList);
//            System.out.println("Order Details Added: "+isAddOrderDeatils);
                if(isAddOrderDeatils){
                    boolean isUpdateItem = itemService.updateItemQuantity(cartItemObservableList);
//                System.out.println("Item Quantity Updated: "+isUpdateItem);
                    if(isUpdateItem){
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }


    }
}
