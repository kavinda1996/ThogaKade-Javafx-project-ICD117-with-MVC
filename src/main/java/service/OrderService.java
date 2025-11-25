package service;

import model.dto.Orders;

import java.sql.SQLException;

public interface OrderService {
    boolean addOrder(Orders orders) throws SQLException;

}
