package repository.impl;

import db.DBConnection;
import model.dto.Orders;
import repository.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public boolean addOrder(Orders orders) throws SQLException {
        String SQL = "INSERT INTO orders Values(? ,? ,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,orders.getOrderId());
        psTm.setObject(2,orders.getOrderDate());
        psTm.setObject(3,orders.getCustomerId());

        return psTm.executeUpdate() > 0;
    }

}
