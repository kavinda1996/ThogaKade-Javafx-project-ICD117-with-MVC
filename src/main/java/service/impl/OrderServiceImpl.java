package service.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.dto.Item;
import model.dto.Orders;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.OrderService;

import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository = new OrderRepositoryImpl();
    @Override
    public boolean addOrder(Orders orders) throws SQLException {

       return orderRepository.addOrder(orders);
    }
}
